//单元格信
export class Cell{
    public r;
    public c;
    public x;
    public y
    constructor(r:number,c:number){
        this.r = r;
        this.c = c;
        this.x = c + 0.5;
        this.y = r + 0.5;
    }
}