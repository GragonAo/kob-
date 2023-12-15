import type { GameMap } from '@/assets/scripts/GameMap';
import { defineStore } from 'pinia';

// 定义 store 的状态类型
interface State {
  gameObject: GameMap | null;
}

// 定义 store
export const useInstaceStore = defineStore({
  id: 'instace',
  state: (): State => ({
    gameObject: null,
  }),
  actions: {
    setGameObject(gameObject: GameMap | null) {
      this.gameObject = gameObject;
    },
  },
});