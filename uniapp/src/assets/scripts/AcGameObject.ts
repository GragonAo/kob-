import { useGameMapStore } from "@/stores/modules/gameMap";
// 游戏物体存放的数组
const AC_GAME_OBJECTS: AcGameObject[] = [];
export class AcGameObject {
    /** 时间间隔 */
    public timedelta: number;
    /** 是否是第一次加载 */
    public has_called_start: boolean;

    /** 构造函数 */
    constructor() {
        // 将游戏物体加入刷新队列
        AC_GAME_OBJECTS.push(this);
        // 时间间隔
        this.timedelta = 0;
        // 是否是第一次加载
        this.has_called_start = false;
    }

    /** 游戏物体启动的第一帧时调用 */
    start(): void { }

    /** 游戏物体每帧调用 */
    update(): void { }

    /** 游戏物体销毁前调用 */
    on_destroy(): void { }

    /** 游戏物体销毁时调用 */
    destroy(): void {
        // 销毁前
        this.on_destroy();
        // 找到游戏物体进行释放销毁
        const index = AC_GAME_OBJECTS.indexOf(this);
        if (index !== -1) {
            AC_GAME_OBJECTS.splice(index, 1);
        }
    }
}
// 使用游戏状态存储
const gameMapStore = useGameMapStore();
export function loop(){
    // 上一次执行的时刻
    let lastTimestamp = Date.now();
    const step = () => {
        // 计算时间间隔
        const now = Date.now();
        const timedelta = lastTimestamp ? now - lastTimestamp : 0;
        lastTimestamp = now;
        gameMapStore?.gameMapInfo?.clearRect(0,0,400,400);
        // 更新所有游戏物体
        AC_GAME_OBJECTS.forEach(obj => {
            obj.timedelta = timedelta;
            if (!obj.has_called_start) {
                obj.has_called_start = true;
                obj.start();
            } else {
                obj.update();
            }
        });
        gameMapStore?.gameMapInfo?.draw();
    };
        
    // 使用 setInterval 设置游戏循环，每秒更新约 60 次
    setInterval(step, 1000 / 40);
};