<template>
	<view>
		<view class="btn-box">
			<view class="center-circle" @click="clickCenter">
				<text class="op">{{ gameStore.playerOp }}</text>
				<view class="up" @click="clickUp">
					<image class="img" src="../../../static/images/up.png" />
				</view>
				<view class="right" @click="clickRight">
					<image class="img" src="../../../static/images/right.png" />
				</view>
				<view class="down" @click="clickDown">
					<image class="img" src="../../../static/images/down.png" />
				</view>
				<view class="left" @click="clickLeft">
					<image class="img" src="../../../static/images/left.png"></image>
				</view>
			</view>
		</view>
	</view>
</template>
  
<script setup lang="ts">
import { useGameStore } from '@/stores/modules/game';
import { defineEmits } from 'vue';
const gameStore = useGameStore();

const emits = defineEmits<{
	(e: "clickUp", data: string): void;
	(e: "clickRight", data: string): void;
	(e: "clickDown", data: string): void;
	(e: "clickLeft", data: string): void;
	(e: "clickCenter", data: string): void;
}>();
const clickUp = () => {
	emits("clickUp", "clickUp");
};

const clickRight = () => {
	emits("clickRight", "clickRight");
};

const clickDown = () => {
	emits("clickDown", "clickDown");
};

const clickLeft = () => {
	emits("clickLeft", "clickLeft");
};

const clickCenter = () => {
	emits("clickCenter", "clickCenter");
};
</script>
  
<style lang='scss' scoped>
.btn-box {
	width: 350rpx;
	height: 350rpx;
	border-radius: 50%;
	border: 10px solid #CCC;
	display: flex;
	justify-content: center;
	align-items: center;

	.img {
		width: 120rpx;
		height: 120rpx;
	}

	.up {
		position: absolute;
		bottom: 95%;
	}

	.op {
		position: absolute;
	}

	.right {
		position: absolute;
		left: 95%;
	}

	.down {
		position: absolute;
		top: 95%;
	}

	.left {
		position: absolute;
		right: 95%;
	}

	.center-circle {
		background-color: aquamarine;
		width: 120rpx;
		height: 120rpx;
		border-radius: 50%;
		position: relative;
		display: flex;
		justify-content: center;
		align-items: center;
	}
}
</style>
  