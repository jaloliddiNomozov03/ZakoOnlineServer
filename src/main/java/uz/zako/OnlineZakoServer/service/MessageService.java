package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import uz.zako.OnlineZakoServer.entity.Message;
import uz.zako.OnlineZakoServer.model.Result;

public interface MessageService {
    Result save(Message message);
    Result delete(Long id);
    Page<Message> findMessagePage(int page, int size);
}
