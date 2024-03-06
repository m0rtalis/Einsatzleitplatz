import createClient from "openapi-fetch";
import type {paths} from "./elp";

export const client = createClient<paths>({baseUrl: 'http://localhost:8080/' });
