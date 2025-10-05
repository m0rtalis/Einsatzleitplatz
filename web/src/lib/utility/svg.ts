import { isEmpty } from '$lib/js';

abstract class Element {
	private readonly attributes: Record<string, string> = {};
	private readonly styles: Record<string, string> = {};

	constructor(readonly name: string) {
	}

	addAttr(key: string, value: string | number): this {
		this.attributes[key] = value.toString();
		return this;
	}

	addStyle(key: string, value: string | number): this {
		this.styles[key] = value.toString();
		return this;
	}

	protected getOpenTag(autoclose: boolean = true): string {
		return `<${this.name}`
			+ Object.entries(this.attributes).map(([key, value]) => ` ${key}='${value}'`).join('')
			+ (isEmpty(this.styles) ? '' : 'style=\'' + Object.entries(this.styles)
				.map(([key, value]) => ` ${key}='${value}'`)
				.join(';') + '\'')
			+ (autoclose ? '/>' : '>');
	}

	protected getCloseTag(): string {
		return `</${this.name}>`;
	}

	abstract toSvgString(): string;
}

class Container extends Element {
	private readonly children: Element[] = [];

	addChild(child: Element): this {
		this.children.push(child);
		return this;
	}

	addChildren(children: Element[]): this {
		this.children.push(...children);
		return this;
	}

	toSvgString(): string {
		return this.getOpenTag(false)
			+ this.children.map((child: Element) => child.toSvgString()).join('\n')
			+ this.getCloseTag();
	}
}

class TextNode extends Element {
	private readonly text: string[] = [];

	constructor(name?: string) {
		super(name ?? 'text');
	}

	addChild(text: string): this {
		this.text.push(text);
		return this;
	}

	toSvgString(): string {
		return this.getOpenTag(false)
			+ '<![CDATA['
			+ this.text.join('\n')
			+ ']]>'
			+ this.getCloseTag();
	}
}

export class LeafElement extends Element {

	toSvgString(): string {
		return this.getOpenTag(true);
	}
}

export class RawElement extends Element {
	constructor(readonly svg: string[]) {
		super('StringElement');
	}

	toSvgString(): string {
		return this.svg.join('\n');
	}
}

export class SVG extends Container {
	private readonly defs = new Container('defs');
	private readonly styleDocument = new TextNode('style');

	constructor() {
		super('svg');
		this.addAttr('xmlns', 'http://www.w3.org/2000/svg');
		this.addChild(this.styleDocument);
		this.addChild(this.defs);
	}

	addDef(element: Element): this {
		this.defs.addChild(element);
		return this;
	}

	addStyleDocument(text: string): this {
		this.styleDocument.addChild(text);
		return this;
	}

	static g(id?: string): Container {
		const c = new Container('g');
		if (id) {
			c.addAttr('id', id);
		}
		return c;
	}

	static text(text: string): TextNode {
		const node = new TextNode();
		node.addChild(text);
		return node;
	}

	static use(href: string): LeafElement {
		return new LeafElement('use').addAttr('href', href);
	}


	static clipPath(id: string): Container {
		const c = new Container('clipPath');
		c.addAttr('id', id);
		return c;
	}
}
