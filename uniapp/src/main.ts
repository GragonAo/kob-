import { createSSRApp, onMounted } from "vue";
import App from "./App.vue";
import { createPinia } from 'pinia';
import pinia from "./stores";
import { useUserStore } from './stores';

export function createApp() {
  const app = createSSRApp(App);
  app.use(pinia);
  //用于解决web端页面拦截
  // #ifdef H5
  window.addEventListener('popstate', () => {
    const currentFullUrl = window.location.href; // 获取完整的URL
    const whiteList = [
      'http://localhost:5173/#/',
      'http://localhost:5173',
      'http://localhost:5173/#/pages/register/register',
    ];
    if (!whiteList.includes(currentFullUrl)) {
      window.location.href = '/';
    }
  });
  // #endif

  return {
    app,
  };
}
