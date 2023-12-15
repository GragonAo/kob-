import { GameState } from "@/enums/game";
import { useGameStore } from "@/stores/modules/game";
import type AnimationFramePolyfill from "@/utils/AnimationFramePolyfill";
// 游戏物体存放的数组
const AC_GAME_OBJECTS: AcGameObject[] = [];
export class AcGameObject {
    private static nextId = 0;
    public readonly acId: number;
    /** 时间间隔 */
    public timedelta: number;
    /** 是否是第一次加载 */
    public has_called_start: boolean;

    /** 构造函数 */
    constructor() {
        this.acId = AcGameObject.nextId++;
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
        const index = AC_GAME_OBJECTS.findIndex(obj => obj.acId === this.acId);
        // 找到游戏物体进行释放销毁
        if (index !== -1) {
            AC_GAME_OBJECTS.splice(index, 1);
            AcGameObject.nextId--;
        }
    }

}
// 使用游戏状态存储
const gameStore = useGameStore();
export function loop(animationFramePolyfill:AnimationFramePolyfill) {
    // 上一次执行的时刻
    let lastTimestamp = 0;
    let animationId = -1;
    const step = (timestamp: DOMHighResTimeStamp) => {
        //console.log("step run");
        if (!lastTimestamp) lastTimestamp = timestamp;
        // 计算时间间隔
        const timedelta = timestamp - lastTimestamp;
        lastTimestamp = timestamp;
        if (gameStore.gameState === GameState.Game) {
            gameStore?.gameCanvas?.ctx?.clearRect(0, 0, gameStore.gameCanvas.width, gameStore.gameCanvas.height);
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
            gameStore.gameCanvas?.ctx?.draw();
            animationId = animationFramePolyfill.requestAnimationFrame(step);
        } else {
            if (animationId !== -1) {
                animationFramePolyfill.cancelAnimationFrame(animationId);
                animationId = -1;
            }
        }
    };
    return animationFramePolyfill.requestAnimationFrame(step);
};

