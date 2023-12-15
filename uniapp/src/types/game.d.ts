import type { GameRusult } from "@/enums/game"
import type { UserInfo } from "./user"

/** 游戏画布信息 */
export type GameCanvas = {
    ctx:UniApp.CanvasContext,
    width:number,
    height:number,
}
export type PlayerOperate = {
    dir_list:GameMoveInfo[]
}
export type GameMoveInfo=PlayerInfo & {
    op:number
}
export type GameResultInfo={
    result:GameRusult
}

export type GameCountDown= {
    time:number
}
export type PlayerInfo={
    id:number,
    row:number,
    col:number
}
export type UsersInfo={
    user_list:UserInfo[]
}

export type GameInfo={
    game_id:string,
    game_map:number[][],
    player_items:PlayerInfo[]
}

export type ScoketInfo={
    event:string
    data:T
}