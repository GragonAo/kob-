package com.kob.backend.controller;

import com.kob.backend.pojo.BattleRecord;
import com.kob.backend.request.BaseRequest;
import com.kob.backend.request.BaseRequest.PageResult;
import com.kob.backend.request.Result;
import com.kob.backend.service.BattleRecordService;
import com.kob.backend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/")
    public Result<PageResult<List<BattleRecord>>> getBattleRecordList(@Validated @RequestBody BaseRequest.PageParams pageParams){
        PageResult<List<BattleRecord>> pageResult = new PageResult<>();

        try{
            Map<String,Object> data = ThreadLocalUtil.get();
            Integer id = (Integer) data.get("id");
            List<BattleRecord> list = battleRecordService.getBattleRecordList(id,pageParams.getPage(),pageParams.getPageSize());
            pageResult.setItems(list);
            Integer count =battleRecordService.getBattleRecordCount(id);
            pageResult.setCounts(count);
            pageResult.setPages(count/pageParams.getPageSize());
            pageResult.setPageSize(pageParams.getPageSize());
            pageResult.setPage(pageParams.getPage()/count +1);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("获取对战信息异常");
        }
        return Result.success(pageResult);
    }
}
