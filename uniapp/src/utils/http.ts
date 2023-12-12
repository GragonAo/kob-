import { useUserStore } from "@/stores";

const baseURL = 
'http://127.0.0.1:3300';

//添加拦截器
const httpInterceptor = {
    //拦截前触发
    invoke(options:UniApp.RequestOptions){
        //1. 非 http 开头需拼接基地址
        if(!options.url.startsWith('http')){
            options.url = baseURL+options.url;
        }
        //2. 请求超时时间，默认20s 系统默认60s
        options.timeout = 20000;
        //3. 添加小程序端请求头标识
        options.header = {
            ...options.header,
            'source-client':'miniapp',
        }
        //4. 添加token请求头标识
        const userStore = useUserStore();
        const token = userStore.profile?.token;
        if(token){
            options.header.Authorization = token;
        }
        console.log(options);
    },
}
uni.addInterceptor('request',httpInterceptor);
uni.addInterceptor('uploadFile',httpInterceptor);

//固定格式
interface Data<T>{
    code:string
    msg:string
    result: T
}
//添加类型，支持泛型
export const http = <T>(option : UniApp.RequestOptions)=>{
    //1. 返回 Promise 对象
    return new Promise<Data<T>>((resolve,reject)=>{
        uni.request({
            ...option,
            //2. 请求成功
            success(res){
                if(res.statusCode>=200 && res.statusCode<300){
                    //2.1 提取核心数据 res.data
                    resolve(res.data as Data<T>)
                }else if(res.statusCode === 401){
                    //401错误 清理用户信息，跳转到登入页面
                    const userStore = useUserStore();
                    userStore.clearProfile();
                    uni.navigateTo({url:'pages/login/login'});
                    reject(res);
                }else{
                    //其他错误 根据后端错误信息提示
                    uni.showToast({
                        title: (res.data as Data<T>).msg || '请求错误',
                        icon: 'none'
                    });
                    reject(res);
                }
            },
            //响应失败
            fail(err){
                uni.showToast({
                    title:'网络错误',
                    icon:'none'
                });
                reject(err);
            }
        })
    })
}