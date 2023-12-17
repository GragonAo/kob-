import type { BattleRecordInfo, PageParams, PageResult } from "@/types/battleRecord"
import { http } from "@/utils/http"

export const getBattleRecordAPI = (data:PageParams)=>{
    return http<PageResult<BattleRecordInfo>>({
        method:'POST',
        url:'/battleRecord/',
        data
    })
}