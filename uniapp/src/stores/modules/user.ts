import type { UserInfo } from "@/types/user";
import { defineStore } from "pinia";
import { ref } from "vue";
/**用户store */
export const useUserStore = defineStore(
    'user',
    ()=>{
        /** 用户信息 */
        const profile = ref<UserInfo>()
        /** 设置用户信息 */
        const setProfile =(val:UserInfo)=>{
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