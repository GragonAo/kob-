import type { RatingInfo } from "@/types/rating"
import { http } from "@/utils/http"

/** 获取排行榜集合 */
export const getRatingListAPI = ()=>{
    return http<RatingInfo[]>({
        method:'GET',
        url:'/rating/'
    })
}