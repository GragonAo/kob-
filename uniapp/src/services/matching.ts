import { http } from "@/utils/http"
/** 将用户加入到匹配池API */
export const addPlayerToMatchingAPI= ()=>{
    return http({
        method:'GET',
        url:'/matching/player/add'
    })
}
/** 将用户从匹配池中移除API */
export const removePlayerToMatchingAPI=()=>{
    return http({
        method:'GET',
        url:'/matching/player/remove'
    })
}