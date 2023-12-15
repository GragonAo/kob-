import { baseURL } from "./http"

export function checkFile(url:string | undefined):string{
    if(url == undefined)url = "";
    if(!url.startsWith('http')){
        url = baseURL+url;
    }
    return url
}