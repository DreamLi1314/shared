package com.geoviswtx.service;

import com.alibaba.fastjson.JSONObject;
import com.geoviswtx.dto.AppCallbackDto;
import com.geoviswtx.entity.*;
import com.geoviswtx.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DataProcessService {

    private final SendGiftRepository sendGiftRepository;
    private final LiveLikeRepository likeRepository;
    private final MessageRepository messageRepository;
    private final LiveCommentRepository commentRepository;
    private final FollowRepository followRepository;

    public DataProcessService(SendGiftRepository sendGiftRepository,
                              LiveLikeRepository likeRepository,
                              MessageRepository messageRepository,
                              LiveCommentRepository commentRepository,
                              FollowRepository followRepository)
    {
        this.sendGiftRepository = sendGiftRepository;
        this.likeRepository = likeRepository;
        this.messageRepository = messageRepository;
        this.commentRepository = commentRepository;
        this.followRepository = followRepository;
    }

    public void saveLike(AppCallbackDto dto) {
        MessageEntity message = new MessageEntity();
        BeanUtils.copyProperties(dto, message);
        BeanUtils.copyProperties(dto.getData(), message);

        message = messageRepository.save(message);

        List<Object> payload = dto.getData().getPayload();
        List<LiveLikeEntity> entities = new ArrayList<>();

        for (Object item : payload) {
            LiveLikeEntity likeEntity = JSONObject.parseObject(JSONObject.toJSONString(item), LiveLikeEntity.class);
            likeEntity.setMessageId(message.getId());
            entities.add(likeEntity);
        }

        entities = likeRepository.saveAll(entities);

        log.info("保存了 {} 条点赞消息!", entities.size());
    }

    public void saveComment(AppCallbackDto dto) {
        MessageEntity message = new MessageEntity();
        BeanUtils.copyProperties(dto, message);
        BeanUtils.copyProperties(dto.getData(), message);

        message = messageRepository.save(message);

        List<Object> payload = dto.getData().getPayload();
        List<LiveCommentEntity> entities = new ArrayList<>();

        for (Object item : payload) {
            LiveCommentEntity entity = JSONObject.parseObject(JSONObject.toJSONString(item), LiveCommentEntity.class);

            BeanUtils.copyProperties(item, entity);
            entity.setMessageId(message.getId());
            entities.add(entity);
        }

        entities = commentRepository.saveAll(entities);

        log.info("保存了 {} 条 comment 消息!", entities.size());
    }

    public void saveGift(AppCallbackDto dto) {
        MessageEntity message = new MessageEntity();
        BeanUtils.copyProperties(dto, message);
        BeanUtils.copyProperties(dto.getData(), message);

        message = messageRepository.save(message);

        List<Object> payload = dto.getData().getPayload();
        List<SendGiftEntity> entities = new ArrayList<>();

        for (Object item : payload) {
            SendGiftEntity entity = JSONObject.parseObject(JSONObject.toJSONString(item), SendGiftEntity.class);

            BeanUtils.copyProperties(item, entity);
            entity.setMessageId(message.getId());
            entities.add(entity);
        }

        entities = sendGiftRepository.saveAll(entities);

        log.info("保存了 {} 条 Gift 消息!", entities.size());
    }


    public void saveFollow(AppCallbackDto dto) {
        MessageEntity message = new MessageEntity();
        BeanUtils.copyProperties(dto, message);
        BeanUtils.copyProperties(dto.getData(), message);

        message = messageRepository.save(message);

        List<Object> payload = dto.getData().getPayload();
        List<FollowEntity> entities = new ArrayList<>();

        for (Object item : payload) {
            FollowEntity entity = JSONObject.parseObject(JSONObject.toJSONString(item), FollowEntity.class);

            BeanUtils.copyProperties(item, entity);
            entity.setMessageId(message.getId());
            entities.add(entity);
        }

        entities = followRepository.saveAll(entities);

        log.info("保存了 {} 条 Follow 消息!", entities.size());
    }
}
