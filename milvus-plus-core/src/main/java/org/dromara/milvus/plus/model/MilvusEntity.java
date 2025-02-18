package org.dromara.milvus.plus.model;

import io.milvus.v2.common.ConsistencyLevel;
import io.milvus.v2.common.IndexParam;
import io.milvus.v2.service.collection.request.AddFieldReq;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import lombok.Data;

import java.util.List;

/**
 * @author xgc
 **/
@Data
public class MilvusEntity {
    private String collectionName;
    private List<String> alias;
    private List<IndexParam> indexParams;
    private List<AddFieldReq> milvusFields;
    private List<String> partitionName;
    private ConsistencyLevel consistencyLevel;
    private Boolean enableDynamicField;
    private List<CreateCollectionReq.Function> functions;
}
