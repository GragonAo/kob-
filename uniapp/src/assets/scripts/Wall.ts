//export 引入时需要加上 { }
import { AcGameObject } from "./AcGameObject";
import type { GameMap } from "./GameMap";

// extends 继承
export class Wall extends AcGameObject {
    public r:number;
    public c:number;
    private gamemap:GameMap;
    private color:string;
    constructor(r:number, c:number, gamemap:GameMap) {
        //调用父类函数
        super();
        this.r = r;
        this.c = c;
        this.gamemap = gamemap;
        this.color = "#B37226";
    }
    render() {
        /*使用const定义的变量保存的是一个地址值，
        这个地址指向一个对象引用。const保证这个地址值是不可变的，
        但对象本身是可变的，所以可以变更这个对象内部的属性。*/
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;
        //绘制颜色
        ctx.fillStyle = this.color;
        //绘制坐标
        ctx.fillRect(this.c * L, this.r * L, L, L);
    }
}