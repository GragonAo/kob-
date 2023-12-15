import { useUserStore } from "@/stores";
import { socketMessageHnadle } from "./SocketMessageHandle";

const userStore = useUserStore();
export class connectWebSocket {
  private wsUrl = 'wss://xmut.shop/websocket/' + userStore.profile?.token;
  //private wsUrl = 'ws://127.0.0.1:3300/websocket/' + userStore.profile?.token;
  /** 创建WebSocket连接 */
  socketTask(): void {
    uni.connectSocket({
      url: this.wsUrl,
      success() {
        console.log('WebSocket连接成功');
      },
      fail() {
        console.log('WebSocket连接失败');
      }
    });
    this.onSocketOpen();
    this.onSocketMessage();
    this.onSocketError();
    this.onSocketClose();
  }
  /** 监听WebSocket连接打开事件 */
  onSocketOpen(): void {
    uni.onSocketOpen(function (res) {
      console.log('WebSocket连接已打开！');
      // 连接成功后发送一条测试消息
      uni.sendSocketMessage({
        data: 'Hello, WebSocket!'
      });
    });
  }
  /** 监听WebSocket接收到服务器的消息事件 */
  onSocketMessage(): void {
    let resp: string = "";
    uni.onSocketMessage(function (res) {
      socketMessageHnadle(res.data);
    });
  }
  /** 监听WebSocket错误 */
  onSocketError(): void {
    uni.onSocketError(function (res) {
      console.log('WebSocket连接打开失败，请检查！');
    });
  }
  /** 监听WebSocket关闭 */
  onSocketClose(): void {
    uni.onSocketClose(function (res) {
      console.log('WebSocket 已关闭！');
    });
  }
  /** 关闭WebSocket连接 */ 
  closeSocket(): void {
    uni.closeSocket({
      success() {
        console.log('WebSocket连接已关闭');
      },
      fail() {
        console.log('WebSocket连接关闭失败');
      }
    });
  }
}