export default class AnimationFramePolyfill {
    private lastTime = Date.now();

    requestAnimationFrame = (callback: FrameRequestCallback): number => {
        let currTime = Date.now();
        let timeToCall = Math.max(0, 40 - (currTime - this.lastTime));
        let id = setTimeout(() => { callback(currTime + timeToCall); }, timeToCall);
        this.lastTime = currTime + timeToCall;
        return id;
    }
    
    cancelAnimationFrame = (id: number): void => {
        clearTimeout(id);
    }
}
