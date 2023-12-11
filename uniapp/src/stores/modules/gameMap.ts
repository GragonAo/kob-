import { defineStore } from "pinia";
import { ref } from "vue";

export const useGameMapStore = defineStore(
    'gameMap',
    ()=>{
        /** 用户信息 */
        const gameMapInfo = ref<UniApp.CanvasContext>()
        /** 设置用户信息 */
        const setGameMapInfo =(val:UniApp.CanvasContext)=>{
            gameMapInfo.value = val;
        }
        /** 清空用户信息 */
        const clearGameMapInfo = ()=>{
            gameMapInfo.value = undefined;
        }

        return{
            gameMapInfo,
            setGameMapInfo,
            clearGameMapInfo,
        }

    }
)