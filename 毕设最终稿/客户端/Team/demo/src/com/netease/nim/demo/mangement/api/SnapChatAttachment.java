package com.netease.nim.demo.mangement.api;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.netease.nim.demo.session.extension.CustomAttachParser;
import com.netease.nim.demo.session.extension.CustomAttachmentType;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;

public class SnapChatAttachment extends FileAttachment {

    private static final String KEY_PATH = "path";
    private static final String KEY_SIZE = "size";
    private static final String KEY_MD5 = "md5";
    private static final String KEY_URL = "url";

    public SnapChatAttachment() {
        super();
    }

    public SnapChatAttachment(JSONObject data) {
        load(data);
    }

    @Override
    public String toJson(boolean send) {
        JSONObject data = new JSONObject();
        try {
            // 重发使用本地路径
            if (!send && !TextUtils.isEmpty(path)) {
                data.put(KEY_PATH, path);
            }

            if (!TextUtils.isEmpty(md5)) {
                data.put(KEY_MD5, md5);
            }
            // 注意：这段代码一定要写。
            // SDK在调toJson的时候 父类FileAttachemnt的url才有值。
            // 这个值是sdk自动赋值的。
            data.put(KEY_URL, url);
            data.put(KEY_SIZE, size);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CustomAttachParser.packData(CustomAttachmentType.SnapChat, data);
    }

    private void load(JSONObject data) {
        path = data.getString(KEY_PATH);
        md5 = data.getString(KEY_MD5);
        url = data.getString(KEY_URL);
        size = data.containsKey(KEY_SIZE) ? data.getLong(KEY_SIZE) : 0;
    }
}