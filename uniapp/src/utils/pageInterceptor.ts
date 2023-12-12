import { useUserStore } from "@/stores"

// 页面白名单
const whiteList = [
    '/',
    '/pages/login/login',
    '/pages/register/register',
]
const userStore =  useUserStore();
function hasPermission (url: string) {
    // 在白名单中或有token，直接跳转
    if(whiteList.indexOf(url) !== -1 || userStore.profile?.token) {
        return true
    }
    return false
}
const interceptor = {
// 页面跳转前进行拦截, invoke根据返回值进行判断是否继续执行跳转
    invoke (options:UniApp.SwitchTabOptions | UniApp.NavigateToOptions
        | UniApp.RedirectToOptions | UniApp.ReLaunchOptions) {
        if(!hasPermission(options.url as string)){
            uni.reLaunch({
                url: '/pages/login/login'
            })
            return false
        }
        return true
    },
    success (options:UniApp.SwitchTabOptions | UniApp.NavigateToOptions) {
        console.log(options)
    }
}
uni.addInterceptor('navigateTo',interceptor);
uni.addInterceptor('switchTab', interceptor);
uni.addInterceptor('redirectTo', interceptor);
uni.addInterceptor('reLaunch', interceptor);
