import { isEmpty } from '$lib/js';

export type Point = { x: number, y: number }
export type Area = { topLeft: Point, bottomRight: Point }

export type AreaInfo = Area & { midPoint: Point, width: number, height: number }

export const areaInfo = (area: Area): AreaInfo => {
	const width = area.bottomRight.x - area.topLeft.x;
	const height = area.bottomRight.y - area.topLeft.y;

	return {
		...area,
		midPoint: { x: area.bottomRight.x - width / 2, y: area.bottomRight.y - height / 2 },
		width: width,
		height: height
	};
};

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
			+ (isEmpty(this.styles) ? '' : 'style="' + Object.entries(this.styles)
				.map(([key, value]) => `${key}:${value}`)
				.join('; ') + '"')
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

	static rect(area: Area): LeafElement {
		return new LeafElement('rect').addAttr('x', area.topLeft.x)
			.addAttr('y', area.topLeft.y)
			.addAttr('width', area.bottomRight.x - area.topLeft.x)
			.addAttr('height', area.bottomRight.y - area.topLeft.y);
	}

	static circle(point: Point, radius: number): LeafElement {
		return new LeafElement('circle').addAttr('cx', point.x).addAttr('cy', point.y).addAttr('r', radius);
	}

	static line(point1: Point, point2: Point): LeafElement {
		return new LeafElement('line').addAttr('x1', point1.x)
			.addAttr('y1', point1.y)
			.addAttr('x2', point2.x)
			.addAttr('y2', point2.y);
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
