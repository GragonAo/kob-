
import { GameRusult, GameState } from "@/enums/game";
import { useGameStore } from "@/stores/modules/game";
import { useInstaceStore } from "@/stores/modules/instace";
import type { GameCountDown, GameInfo, GameResultInfo, PlayerOperate, ScoketInfo, UsersInfo } from "@/types/game";
const gameStore = useGameStore();
const instaceStore = useInstaceStore();
/** 处理服务端发来的消息 */
export const socketMessageHnadle = (resp: string) => {
    const json: ScoketInfo = JSON.parse(resp);
    //console.log('收到服务器内容：' + json.event + "\n" + json.data);
    if (json.event === "users-in-game") {
        const data: UsersInfo = JSON.parse(json.data);
        gameStore.setUserList(data.user_list);
    }
    if (json.event === "start-game") {
        const data: GameInfo = JSON.parse(json.data);
        gameStore.setGameInfo(data);
        gameStore.setGameState(GameState.Game);
    }
    if (json.event === "game-countdown") {
        const data: GameCountDown = JSON.parse(json.data);
        gameStore.setCountDownTime(data);
    }
    if (json.event === "game-result") {
        const data: GameResultInfo = JSON.parse(json.data);
        gameStore.setGameRusult(data);
        uni.showToast({
            title:gameStore.gameRusult?.result == GameRusult.ALL? 'WIN' : gameStore.gameRusult?.result.toString(),
            icon: 'none',
            success:()=>{
                gameStore.initGameStore();
                instaceStore.gameObject?.destroy();
                instaceStore.setGameObject(null);
                gameStore.setGameState(GameState.Null);
            }
        })
    }
    if (json.event === 'game-move') {
        const data: PlayerOperate = JSON.parse(json.data);
        gameStore.setPlayerOperate(data.dir_list);
        instaceStore.gameObject?.setSnakesDir();
        gameStore.playerOp = '未输入';
        //console.log("json.data的内容为：", json.data);
        //console.log("解析后的data为：", data);
        //console.log(data.dir_list[0].id,data.dir_list[0].op,data.dir_list[0].col,data.dir_list[0].row);
    }
}