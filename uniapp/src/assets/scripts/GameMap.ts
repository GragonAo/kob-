import { AcGameObject } from "./AcGameObject";
import type { Cell } from "./Cell";
import { Snake } from "./Snake";
import { Wall } from "./Wall";
import { STATE } from "./types/enums";

export class GameMap extends AcGameObject {
    //行列
    private rows:number = 13;
    private cols:number = 14;
    //高宽比率
    public L:number = 0;
    //生产障碍物数量
    private inner_walls_count:number = 20;
    //障碍物存放数组
    private walls:Wall[] = [];
    //蛇类
    private snakes:Snake[] = [];
    public ctx;
    // private store;
    /** 构成函数 */
    constructor(ctx:UniApp.CanvasContext) {
        super();
        this.ctx = ctx;
        this.snakes.push(new Snake({ id: 0, color: "#F94848", r: this.rows - 2, c: 1 }, this));
        this.snakes.push(new Snake({ id: 1, color: "#4876EC", r: 1, c: this.cols - 2 }, this));
    }

    /** 创建障碍物墙体 */
    create_walls() {
        let g: number[][] = [];
        for (let r = 0; r < this.rows; r++) {
            g[r] = []; // 初始化二维数组的每一行
            for (let c = 0; c < this.cols; c++) {
                g[r][c] = (r + c) % 2 == 0?1:0;
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }
    }
    /** 添加事件监听 */
    add_listening_events() {

    }
    /** 第一帧时调用 */
    override start() {
        this.create_walls();
        this.add_listening_events();
    }
    /** 动态计算地图的生成大小，实现自适应浏览器窗口大小 */
    update_size() {
        //计算最小的高宽比率
        this.L = parseInt(Math.min(400 / this.cols, 400 / this.rows).toString());
    }
    /** 判断俩条蛇是否都准备好下一个回合了 */
    check_ready() {
        for (const snake of this.snakes) {
            //判断是否处于等待阶段
            if (snake.getState() !== STATE.IDLE) return false;
            //判断朝向是否合法
            if (snake.get_direction() === -1) return false;
        }
        return true;
    }
    /** 让俩条蛇进入下一个回合 */
    next_step(d:number) {
        for (const snake of this.snakes) {
            snake.set_direction(d);//测试
            snake.next_step();
        }
    }

    /** 检测目标位置是否合法，没有撞到俩条蛇的身体和障碍物为合法状态 */
    check_valid(cell:Cell) {
        //遍历出障碍物，判断下一步是否为墙体，如果为墙体为不合法状态
        for (const wall of this.walls) {
            if (wall.r === cell.r && wall.c === cell.c) return false;
        }
        for (const snake of this.snakes) {
            let k = snake.cells.length;
            //当蛇尾前进的时候，蛇尾不要判断，因为蛇尾前进后，下一步格子变为合法操作
            if (!snake.check_tail_increasing()) {
                //尾巴不用在判断，所以长度--
                k--;
            }
            //判断是否下一格为蛇身所在格子
            for (let i = 0; i < k; i++) {
                if (snake.cells[i].r === cell.r && snake.cells[i].c === cell.c)
                    return false;
            }
        }
        return true;
    }
    /** 每帧调用 */
    override update() {
        this.update_size();
        if (this.check_ready()) {
            //this.next_step();
        }
        this.render();
    }
    /** 方格渲染 */
    render() {
        // 设置边框样式
        this.ctx.setStrokeStyle("#00ff00");
        this.ctx.setLineWidth(5);
        this.ctx.rect(0, 0, this.cols * this.L, this.rows * this.L);
        this.ctx.stroke();
    
        // 绘制棋盘格
        const color_even = "#AAD751", color_odd = "#A2D149";
        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                // 进行奇偶判断生成不同颜色的格子
                this.ctx.fillStyle = (r + c) % 2 == 0 ? color_even : color_odd;
                // 渲染方格
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
        this.snakes.forEach(snake => {
            snake.render();
        });
        this.walls.forEach(wall => {
            wall.render();
        });
    }
}