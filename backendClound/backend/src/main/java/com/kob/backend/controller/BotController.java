package com.kob.backend.controller;

import com.kob.backend.pojo.Bot;
import com.kob.backend.request.Result;
import com.kob.backend.service.BotService;
import com.kob.backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/bot")
@RestController
public class BotController {
    @Autowired
    private BotService botService;
    @GetMapping("/")
    public Result<List<Bot>> getBotList(){
        List<Bot> botList = new ArrayList<>();
        try{
            Map<String,Object> data = ThreadLocalUtil.get();
            Integer id = (Integer) data.get("id");
            botList = botService.getBotList(id);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
        return Result.success(botList);
    }
    @GetMapping("/{id}")
    public Result<Bot> getBot(@PathVariable("id") Integer id) {
        Bot bot = null;
        try {
            bot = botService.getBot(id);
            Map<String,Object>data = ThreadLocalUtil.get();
            Integer userId = (Integer) data.get("id");
            if(bot.getUserId() != userId){
                return Result.error("你没有权限查询这个Bot");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败");
        }
        return Result.success(bot);
    }

    @PostMapping("/")
    public Result addBot(@Validated @RequestBody Bot bot){
        Map<String,Object> data = ThreadLocalUtil.get();
        Integer userId = (Integer) data.get("id");
        if(bot.getIsDefault() == '1'){
            List<Bot> botList = botService.getBotList(userId);
            if(botList!=null && botList.size()>0){
                Bot b = botList.get(0);
                b.setIsDefault('0');
                botService.updateBot(b,false);
            }
        }
        Integer res = botService.addBot(bot);
        if(res != 1){
            return Result.error("添加Bot错误");
        }
        return Result.success();
    }
    @PutMapping("/")
    public Result updateBot(@Validated(Bot.Update.class) @RequestBody Bot bot){
        Map<String,Object> data = ThreadLocalUtil.get();
        Integer userId = (Integer) data.get("id");
        if(bot.getIsDefault() == '1'){
            List<Bot> botList = botService.getBotList(userId);
            if(botList!=null){
                Bot b = botList.get(0);
                b.setIsDefault('0');
                botService.updateBot(b,false);
            }
        }
        Integer res = botService.updateBot(bot,true);
        if(res != 1)return Result.error("更新Bot失败");
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result deleteBot(@PathVariable("id") Integer id){
        Map<String,Object> data = ThreadLocalUtil.get();
        Integer userId = (Integer)data.get("id");
        Integer res = botService.deleteBot(id,userId);
        if(res != 1){
            return Result.error("删除Bot失败");
        }
        return Result.success();
    }
}
