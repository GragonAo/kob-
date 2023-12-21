import type { UserInfo } from "@/types/user";
import { checkFile } from "@/utils/checkFile";
import { defineStore } from "pinia";
import { ref } from "vue";
/** 用户相关信息存储器 */
export const useUserStore = defineStore(
    'user',
    ()=>{
        /** 用户信息 */
        const profile = ref<UserInfo>();
        /** 设置用户信息 */
        const setProfile =(val:UserInfo)=>{
            val.photo = checkFile(val.photo);
            profile.value = val;
        }
        /** 清空用户信息 */
        const clearProfile = ()=>{
            profile.value = undefined;
        }

        return{
            profile,
            setProfile,
            clearProfile,
        }

    },
    // 持久化
    {
        //小程序端 web通用
        persist:{
            storage:{
                getItem(key){
                    return uni.getStorageSync(key);
                },
                setItem(key,value){
                    uni.setStorageSync(key,value);
                },
            }
        },
    }

)
/** 用户相关信息存储器类型接口 */
export interface UserStore {
    profile: UserInfo | undefined;
    setProfile: (val: UserInfo) => void;
    clearProfile: () => void;
}