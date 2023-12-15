
import { GameState} from "@/enums/game";
import type { GameMoveInfo, GameCountDown,  GameInfo,  GameResultInfo,  GameCanvas } from "@/types/game";
import type { UserInfo } from "@/types/user";
import { defineStore, type Store } from "pinia";
import { ref } from "vue";

/** 游戏相关信息存储器 */
export const useGameStore = defineStore(
    'game',
    () => {
        /** 游戏画布信息 */
        const gameCanvas = ref<GameCanvas>();
        /** 游戏信息 */
        const gameInfo = ref<GameInfo>();
        /** 游戏倒计时信息 */
        const gameCountDownTime = ref<GameCountDown>();
        /** 游戏玩家操作信息 */
        const playerOperate = ref<GameMoveInfo[]>();
        /** 游戏结果信息 */
        const gameRusult = ref<GameResultInfo>();
        /** 游戏状态信息 */
        const gameState = ref<GameState>();
        /** 游戏中所有玩家信息 */
        const userList = ref<UserInfo[]>();
        /** 初始化游戏存储器 */
        const initGameStore = () => {
            clearCountDownTime();
            clearGameInfo();
            clearGameRusult();
            clearPlayerOperate();
            clearGameState();
            clearUserList();
            setGameState(GameState.Null);
        }
        /** 设置游戏画布信息 */
        const setGameCanvas = (val: GameCanvas) => {
            gameCanvas.value = val;
        }
        /** 设置游戏信息 */
        const setGameInfo = (val: GameInfo) => {
            gameInfo.value = val;
        }
        /** 设置游戏倒计时信息 */
        const setCountDownTime = (val: GameCountDown) => {
            gameCountDownTime.value = val;
        }
        /** 设置游戏玩家信息 */
        const setPlayerOperate = (val: GameMoveInfo[]) => {
            playerOperate.value = val;
        }
        /** 设置游戏结果信息 */
        const setGameRusult = (val: GameResultInfo) => {
            gameRusult.value = val;
        }
        /** 设置游戏状态信息 */
        const setGameState = (val: GameState) => {
            gameState.value = val;
        }
        /** 设置游戏中所有玩家信息 */
        const setUserList = (val:UserInfo[])=>{
            userList.value = val;
        }

        /** 清空游戏画布信息 */
        const clearGameCanvas = () => {
            gameCanvas.value = undefined;
        }
        /** 清空游戏信息 */
        const clearGameInfo = () => {
            gameInfo.value = undefined;
        }
        /** 清空游戏倒计时信息 */
        const clearCountDownTime = () => {
            gameCountDownTime.value = undefined;
        }
        /** 清空游戏玩家信息 */
        const clearPlayerOperate = () => {
            playerOperate.value = undefined;
        }
        /** 清空游戏结果信息 */
        const clearGameRusult = () => {
            gameRusult.value = undefined;
        }
        /** 清空游戏状态信息 */
        const clearGameState = () => {
            gameState.value = undefined;
        }
        /** 清空游戏中所有玩家信息 */
        const clearUserList = ()=>{
            userList.value = undefined;
        }

        return {
            gameInfo, gameCountDownTime, playerOperate, gameRusult, gameState,gameCanvas,userList,
            setCountDownTime, setGameInfo, setGameRusult, setPlayerOperate, setGameState, initGameStore,setGameCanvas,setUserList,
            clearGameRusult, clearCountDownTime, clearGameInfo, clearPlayerOperate, clearGameState,clearGameCanvas,clearUserList
        }

    }
)
/** 游戏相关信息存储器类型接口 */
export interface GameStore extends Store {
    gameCanvas: GameCanvas | undefined;
    gameInfo: GameInfo | undefined;
    gameCountDownTime: GameCountDown | undefined;
    playerOperate: GameMoveInfo[] | undefined;
    gameRusult: GameResultInfo | undefined;
    gameState: GameState | undefined;
    userList: UserInfo[] | undefined;
    setGameCanvas: (val: GameCanvas) => void;
    setGameInfo: (val: GameInfo) => void;
    setCountDownTime: (val: GameCountDown) => void;
    setPlayerOperate: (val: GameMoveInfo[]) => void;
    setGameRusult: (val: GameResultInfo) => void;
    setGameState: (val: GameState) => void;
    setUserList: (val: UserInfo[]) => void;
    initGameStore: () => void;
    clearGameCanvas: () => void;
    clearGameInfo: () => void;
    clearCountDownTime: () => void;
    clearPlayerOperate: () => void;
    clearGameRusult: () => void;
    clearGameState: () => void;
    clearUserList:()=>void;
}

