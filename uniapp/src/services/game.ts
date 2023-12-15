import { http } from "@/utils/http"
/** 游戏中设置下一步操作API */
export const postGameSetNextStepAPI=(data:{game_id:string,operate:number})=>{
    return http({
        method:'POST',
        url:'/game/setNextStep',
        data
    })
}