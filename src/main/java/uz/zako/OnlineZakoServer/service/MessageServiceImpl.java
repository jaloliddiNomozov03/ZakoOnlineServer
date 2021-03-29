package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Message;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.repository.MessageRepository;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Override
    public Result save(Message message) {
        try {
            messageRepository.save(message);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"save failed");
    }
    @Override
    public Result delete(Long id) {
        try {
            messageRepository.deleteById(id);
            return new Result(true, "deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public Page<Message> findMessagePage(int page, int size) {
        try {
            Pageable pageable= PageRequest.of(page,size, Sort.by("createAt").descending());
            return messageRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
