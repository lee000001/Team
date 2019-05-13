package com.netease.nim.demo.mangement.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nim.demo.session.extension.CustomAttachment;
import com.netease.nim.demo.session.extension.CustomAttachmentType;
import com.netease.nim.demo.session.extension.DefaultCustomAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser;


public class CustomAttachParser implements MsgAttachmentParser {

    private static final String KEY_DATA ="keyData" ;

    // 根据解析到的消息类型，确定附件对象类型
    @Override
    public MsgAttachment parse(String json) {
        CustomAttachment attachment = null;
        try {
            JSONObject object = JSON.parseObject(json);
            int type = object.getInteger("type");
            JSONObject data = object.getJSONObject(KEY_DATA);
            switch (type) {
                case CustomAttachmentType.SnapChat:
                    return new SnapChatAttachment(data);
                default:
                    attachment = new DefaultCustomAttachment();
                    break;
            }

            if (attachment != null) {
                attachment.fromJson(data);
            }
        } catch (Exception e) {

        }

        return attachment;
    }

}