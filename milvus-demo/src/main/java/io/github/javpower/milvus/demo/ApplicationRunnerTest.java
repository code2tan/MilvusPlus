package io.github.javpower.milvus.demo;

import com.alibaba.fastjson.JSONObject;
import io.github.javpower.milvus.demo.model.Face;
import io.github.javpower.milvus.demo.test.FaceMilvusMapper;
import io.github.javpower.milvus.plus.model.vo.MilvusResp;
import io.github.javpower.milvus.plus.model.vo.MilvusResultVo;
import io.milvus.v2.service.vector.response.DeleteResp;
import io.milvus.v2.service.vector.response.InsertResp;
import io.milvus.v2.service.vector.response.UpsertResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ApplicationRunnerTest implements ApplicationRunner {
    @Autowired
    private FaceMilvusMapper mapper;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Face face=new Face();
        List<Float> vector = new ArrayList<>();
        // 假设我们有一些浮点数值来填充向量，实际应用中这里应该是人脸特征提取算法得到的数值
        for (int i = 0; i < 128; i++) {
            vector.add((float) (Math.random() * 100)); // 这里仅作为示例使用随机数
        }
        face.setFaceVector(vector);
        face.setPersonId(1l);
        //新增
        MilvusResp<InsertResp> insert = mapper.insert(face);
        log.info("insert--{}", JSONObject.toJSONString(insert));
        //id查询
        MilvusResp<List<Face>> query = mapper.getById(1l);
        log.info("query--getById---{}", JSONObject.toJSONString(query));
        //向量查询
        MilvusResp<MilvusResultVo<Face>> query1 = mapper.queryWrapper()
                .vector(Face::getFaceVector, vector)
                .eq(Face::getPersonId,1l)
                .topK(2)
                .query();
        log.info("query--queryWrapper---{}", JSONObject.toJSONString(query1));
        //更新
        vector.clear();
        for (int i = 0; i < 128; i++) {
            vector.add((float) (Math.random() * 100)); // 这里仅作为示例使用随机数
        }
        MilvusResp<UpsertResp> update = mapper.updateById(face);
        log.info("update--{}", JSONObject.toJSONString(update));

        //id查询
        MilvusResp<List<Face>> query2 = mapper.getById(1l);
        log.info("query--getById---{}", JSONObject.toJSONString(query2));
        //删除
        MilvusResp<DeleteResp> remove = mapper.removeById(1l);
        log.info("remove--{}", JSONObject.toJSONString(remove));

        //查询
        MilvusResp<List<Face>> query3 = mapper.getById(1l);
        log.info("query--{}", JSONObject.toJSONString(query3));

    }
}