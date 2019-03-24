package com.example.demo.web;

import com.example.demo.core.Doc;
import com.example.demo.core.DocUsecase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/docs")
public class DocController {

    private final DocUsecase docUsecase;

    public DocController(DocUsecase docUsecase) {
        this.docUsecase = docUsecase;
    }

    //TODO:PostMapping 으로 변경
    @GetMapping
    public String insertDocTest(){

        List<Doc> docs = new ArrayList<>();

        try{
            //파일 객체 생성
            File file = new File("C:\\Users\\SIEUN\\Desktop\\dic.txt");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";

            Integer id = 0;
            while((line = bufReader.readLine()) != null){
                //System.out.println(line);
                docs.add(new Doc(id, line.split("/")[0]));
                id++;

                if(id == 200){
                    break;
                }
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.
            bufReader.close();

        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }



        docUsecase.insertDocs(docs);
        return "OK";
    }

}
