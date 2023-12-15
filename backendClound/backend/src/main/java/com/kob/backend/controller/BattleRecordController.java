package com.kob.backend.controller;

import com.kob.backend.pojo.BattleRecord;
import com.kob.backend.pojo.Result;
import com.kob.backend.service.BattleRecordService;
import com.kob.backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/battleRecord")
public class BattleRecordController {
    @Autowired
    private BattleRecordService battleRecordService;
    @GetMapping("/{id}")
    public Result<BattleRecord> getBattleRecord(@PathVariable("id") Integer id){
        BattleRecord battleRecord = null;
        try{
            battleRecord = battleRecordService.getBattleRecord(id);
            if(battleRecord == null){
                return Result.error("查询ID不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            return  Result.error("查询出现异常");
        }
        return Result.success(battleRecord);
    }
    @GetMapping("/")
    public Result<List<BattleRecord>> getBattleRecordList(){
        List<BattleRecord> list = null;
        try{
            Map<String,Object> data = ThreadLocalUtil.get();
            Integer id = (Integer) data.get("id");
            list = battleRecordService.getBattleRecordList(id);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("获取对战信息异常");
        }
        return Result.success(list);
    }
}
