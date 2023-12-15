<template>
	<view class="gamemap">
		<canvas ref="canvas" :style="{ width: gameStore.gameCanvas?.width + 'px',
		 height:gameStore.gameCanvas?.height+'px',
		 backgroundColor: 'wheat' }" canvas-id="canvas"></canvas>		
		 <view class="player-input">
		<button @click="onBtnUpClick">
			上
		</button>
		<button @click="onBtnRightClick">
			右
		</button>
		<button @click="onBtnDownClick">
			下
		</button>
		<button @click="onBtnLeftClick">
			左
		</button>
	</view>
	</view>

</template>

<script setup lang="ts">
import { loop } from '@/assets/scripts/AcGameObject';
import { GameMap } from '@/assets/scripts/GameMap';
import { useGameStore } from '@/stores/modules/game';
import { useInstaceStore } from '@/stores/modules/instace';
import AnimationFramePolyfill from '@/utils/AnimationFramePolyfill';
import { ref } from 'vue';
import { onUnmounted } from 'vue';
import { getCurrentInstance } from 'vue';
import { onMounted } from 'vue';
const canvas = ref<null|HTMLCanvasElement>(null);
const gameStore = useGameStore();
const instaceStore = useInstaceStore();
const animationFramePolyfill = ref<AnimationFramePolyfill>()
const animationId = ref(-1);
onMounted(() => {
	console.log("GameMapVue被调用了")
	const instance = getCurrentInstance();
	const ctx = uni.createCanvasContext("canvas", instance);
	gameStore.setGameCanvas({
		ctx:ctx,
		width:400,
		height:400
	})
	animationFramePolyfill.value = new AnimationFramePolyfill();
	animationId.value = loop(animationFramePolyfill.value);
  if(ctx){
    const game = new GameMap(ctx);
	instaceStore.setGameObject(game);
 }
});

onUnmounted(()=>{
	if (animationFramePolyfill.value && animationId.value !== -1) {
        animationFramePolyfill.value.cancelAnimationFrame(animationId.value);
      }
})

const onBtnUpClick = ()=>{
	if(instaceStore.gameObject){
		instaceStore.gameObject?.add_listening_events(0);
	}
}
const onBtnDownClick = ()=>{
	if(instaceStore.gameObject){
		instaceStore.gameObject?.add_listening_events(2);
	}
}
const onBtnLeftClick = ()=>{
	if(instaceStore.gameObject){
		instaceStore.gameObject?.add_listening_events(3);
	}
}
const onBtnRightClick = ()=>{
	if(instaceStore.gameObject){
		instaceStore.gameObject?.add_listening_events(1);
	}
}
</script>

<style scoped>
.gamemap {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.player-input {
  width: 100%;
}
</style>