
<script setup lang="ts">
import HexOptBtn from '../../../components/HexOptBtn/HexOptBtn.vue'
import { loop } from '@/assets/scripts/AcGameObject';
import { GameMap } from '@/assets/scripts/GameMap';
import { useUserStore } from '@/stores';
import { useGameStore } from '@/stores/modules/game';
import { useInstaceStore } from '@/stores/modules/instace';
import AnimationFramePolyfill from '@/utils/AnimationFramePolyfill';
import { getCurrentInstance } from 'vue';
import { watch } from 'vue';
import { computed } from 'vue';
import { onUnmounted } from 'vue';
import { onMounted } from 'vue';
import { ref } from 'vue';


const canvas = ref<null | HTMLCanvasElement>(null);
const gameStore = useGameStore();
const instaceStore = useInstaceStore();
const animationFramePolyfill = ref<AnimationFramePolyfill>()
const animationId = ref(-1);
onMounted(() => {
	console.log("GameMapVue被调用了")
	const instance = getCurrentInstance();
	const ctx = uni.createCanvasContext("canvas", instance);
	gameStore.setGameCanvas({
		ctx: ctx,
		width: 400,
		height: 400
	})
	animationFramePolyfill.value = new AnimationFramePolyfill();
	animationId.value = loop(animationFramePolyfill.value);
	if (ctx) {
		const game = new GameMap(ctx);
		instaceStore.setGameObject(game);
	}
});

onUnmounted(() => {
	if (animationFramePolyfill.value && animationId.value !== -1) {
		animationFramePolyfill.value.cancelAnimationFrame(animationId.value);
	}
})
const onBtnUpClick = () => {
	if (instaceStore.gameObject) {
		instaceStore.gameObject?.add_listening_events(0);
		gameStore.playerOp ='上';
	}
}
const onBtnDownClick = () => {
	if (instaceStore.gameObject) {
		instaceStore.gameObject?.add_listening_events(2);
		gameStore.playerOp ='下';
	}
}
const onBtnLeftClick = () => {
	if (instaceStore.gameObject) {
		instaceStore.gameObject?.add_listening_events(3);
		gameStore.playerOp ='左';
	}
}
const onBtnRightClick = () => {
	if (instaceStore.gameObject) {
		instaceStore.gameObject?.add_listening_events(1);
		gameStore.playerOp ='右';
	}
}
</script>

<template>
	<view class="gamemap">
		倒计时：{{ gameStore.gameCountDownTime?.time }}
		<canvas
      ref="canvas"
      :style="{
        width: gameStore.gameCanvas?.width + 'px',
        height: gameStore.gameCanvas?.height + 'px',
        backgroundColor: 'wheat'
      }"
      canvas-id="canvas"
    ></canvas>
	{{ gameStore.playerOperate}}
		<view class="btn-box">
			<HexOptBtn @clickUp="onBtnUpClick" @clickRight="onBtnRightClick"
				@clickDown="onBtnDownClick" @clickLeft="onBtnLeftClick" />
		</view>
	</view>

</template>
  
<style lang="scss"  scoped>
.gamemap {
	position: relative;
	width: 100%;
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

.btn-box {
	display: flex;
	justify-content: center;
}
</style>