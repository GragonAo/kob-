import type { BotInfo } from "@/types/bot"
import { http } from "@/utils/http"

export const getBotListAPI =()=>{
    return http<BotInfo[]>({
        method:'GET',
        url:'/bot/'
    })
}
export const getBotAPI = (id:number)=>{
    return http<BotInfo>({
        method:'GET',
        url:`/bot/${id}`
    })
}
export const addBotAPI = (data:BotInfo)=>{
    return http({
        method:'POST',
        url:'/bot/',
        data
    })
}
export const delectBotAPI =(id:number)=>{
    return http({
        method:'DELETE',
        url:`/bot/${id}`
    })
}
export const updateBotAPI =(id:number,data:BotInfo)=>{
    data.id = id;
    return http({
        method:'PUT',
        url:`/bot/`,
        data
    })
}