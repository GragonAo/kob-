import type { UserInfo } from "@/types/user"
import { http } from "@/utils/http"
/** Web端登录接口 */
export const postLoginWebeAPI = (username:string,password:string)=>{
    return http<string>({
        method:'POST',
        url:'/user/login',
        data:{
            username,
            password
        }
    })
}
/** 注册接口 */
export const postRegisterAPI =(data:{username:string,pwd:string,re_pwd:string})=>{
    return http({
        method:'POST',
        url:'/user/register',
        data
    })
}
/** 获取用户信息接口 */
export const getUserInfoAPI = ()=>{
    return http<UserInfo>({
        method:'GET',
        url:'/user/userInfo'
    })
}