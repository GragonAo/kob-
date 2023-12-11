import { AcGameObject } from "./AcGameObject";
import { Cell } from "./Cell";
import type { GameMap } from "./GameMap";
import { STATE } from "./types/enums";
export class Snake extends AcGameObject {
    /** 蛇的ID标识 */
    private id: number;
    /** 蛇的颜色 */
    private color: string;
    /** 游戏地图 */
    private gamemap: GameMap;
    /** 用于存放蛇的身体，cells[0]存放蛇头 */
    public cells: Cell[];
    /** 下一步的目标位置 */
    private next_cell: Cell | null = null;
    /** 蛇每秒走5个格子 */
    private speed: number = 5;
    /** 蛇的眼睛方向 */
    private eye_direction: number = 0;
    /** -1表示没有指令，0、1、2、3表示上右下左 */
    private direction: number = -1;
    /** 当前状态 */
    private status: STATE = STATE.IDLE;
    /** 4个x方向的偏移量 */
    private dr: number[] = [-1, 0, 1, 0];
    /** 4个y方面的偏移量 */
    private dc: number[] = [0, 1, 0, -1];
    /** 表示的回合数 */
    private step: number = 0;
    /** 允许的误差值，主要判断两个物体的距离 */
    private eps: number = 1e-2;
    // 蛇眼睛不同方向的x的偏移量
    private eye_dx: number[][] = [
        [-1, 1],
        [1, 1],
        [1, -1],
        [-1, -1],
    ];
    // 蛇眼睛不同方向的y的偏移量
    private eye_dy: number[][] = [
        [-1, -1],
        [-1, 1],
        [1, 1],
        [1, -1],
    ];
    /** 构造函数 */
    constructor(info: { id: number; color: string; r: number; c: number }, gamemap: GameMap) {
        super();
        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;
        // 用于存放蛇的身体，cells[0]存放蛇头
        this.cells = [new Cell(info.r, info.c)];
        // 左下角的蛇初始化朝上，右上角的蛇初始化朝下
        if (this.id === 1) this.eye_direction = 2;
    }
    start() {
        // 实现 start 方法
    }
    /** 返回蛇当前状态 */
    public getState(): STATE {
        return this.status;
    }
    /** 设置蛇的状态 */
    public setState(state: STATE) {
        this.status = state;
    }
    /** 设置蛇的移动方向 */
    set_direction(d: number) {
        this.direction = d;
    }
    /** 获取蛇的移动方向 */
    public get_direction(): number {
        return this.direction;
    }

    // 检测当前回合，蛇的长度是否需要增加，游戏规则为10回合前每次增加1，之后3回合增加1
    check_tail_increasing(): boolean {
        if (this.step <= 10) return true;
        if (this.step % 3 === 1) return true;
        return false;
    }

    // 将蛇的状态变为走下一步
    next_step() {
        const d = this.direction;
        // 蛇下一步的单元格
        this.next_cell = new Cell(this.cells[0].r + this.dr[d], this.cells[0].c + this.dc[d]);
        // 将眼睛方向修改为下一步需要的状态
        this.eye_direction = d;
        // 清空方向
        this.direction = -1;
        // 将状态设置为移动
        this.status = STATE.MOVE;
        // 将回合数增加
        this.step++;

        // 获取蛇的长度
        const k = this.cells.length;

        // 将蛇身体向后移动，把蛇头位置空出来
        for (let i = k; i > 0; i--) {
            // 使用复制值操作，避免引用出错
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i - 1]));
        }
    }
    // 蛇移动
    update_move() {
        // 计算出当前位置与下一步位置的间隙长度
        const dx = this.next_cell!.x - this.cells[0].x;
        const dy = this.next_cell!.y - this.cells[0].y;
        // 求出斜边长度
        const distance = Math.sqrt(dx * dx + dy * dy);
        // 走到了目标点
        if (distance < this.eps) {
            // 添加一个新的蛇头
            this.cells[0] = this.next_cell!;
            // 下一格位置
            this.next_cell = null;
            // 走完了，停下来
            this.status = STATE.IDLE;
            // 如果蛇不变长
            if (!this.check_tail_increasing()) {
                // 将蛇尾巴删除
                this.cells.pop();
            }
        } else {
            // 每两帧之间的距离
            const move_distance = (this.speed * this.timedelta) / 1000;
            // 蛇头的位置信息,朝着计算的角度值进行差值移动
            this.cells[0].x += (move_distance * dx) / distance;
            this.cells[0].y += (move_distance * dy) / distance;

            if (!this.check_tail_increasing()) {
                // 蛇身长度
                const k = this.cells.length;
                // 获取蛇最后一个位置与倒数第二个位置
                const tail = this.cells[k - 1],
                    tail_target = this.cells[k - 2];
                // 获取两个位置的间距
                const tail_dx = tail_target.x - tail.x;
                const tail_dy = tail_target.y - tail.y;
                // 差值移动蛇尾
                tail.x += (move_distance * tail_dx) / distance;
                tail.y += (move_distance * tail_dy) / distance;
            }
        }
    }
    update() {
        // 判断当前状态是否是移动
        if (this.status === STATE.MOVE) {
            this.update_move();
        }
    }
    render() {
        // 获取高宽比率
        const L = this.gamemap.L;
        // 获取画布
        const ctx = this.gamemap.ctx;
        // 设置画笔颜色
        ctx.fillStyle = this.color;
        if (this.status === STATE.DIE) {
            ctx.fillStyle = "white";
        }
        // 蛇身绘制，圆形
        for (const cell of this.cells) {
            // 落笔
            ctx.beginPath();
            // 画圆
            ctx.arc(cell.x * L, cell.y * L, (L / 2) * 0.8, 0, Math.PI * 2);
            // 抬笔
            ctx.fill();
        }
        // 进行蛇身矩形补充的绘制
        for (let i = 1; i < this.cells.length; i++) {
            // 获取两个相邻的身体部分
            const a = this.cells[i - 1],
                b = this.cells[i];
            // 如果两个圆的距离过近，就不需要矩形填充
            if (Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps) continue;
            // 横方向补全
            if (Math.abs(a.x - b.x) < this.eps) {
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y, b.y) * L, L * 0.8, Math.abs(a.y - b.y) * L);
            }
            // 纵方向补全
            else {
                ctx.fillRect(Math.min(a.x, b.x) * L, (a.y - 0.4) * L, Math.abs(a.x - b.x) * L, L * 0.8);
            }
        }
        // 画蛇的眼睛
        ctx.fillStyle = "black";
        for (let i = 0; i < 2; i++) {
            // 蛇眼睛位置
            const eye_x = (this.cells[0].x + this.eye_dx[this.eye_direction][i] * 0.15) * L;
            const eye_y = (this.cells[0].y + this.eye_dy[this.eye_direction][i] * 0.15) * L;
            ctx.beginPath();
            ctx.arc(eye_x, eye_y, L * 0.05, 0, Math.PI * 2);
            ctx.fill();
            ctx.stroke();
        }
    }
}
