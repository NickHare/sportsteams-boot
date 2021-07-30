package nos.sportsteamsboot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import nos.sportsteamsboot.view.DetailedView;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("batch")
public class BatchController {

    @Autowired
    JobOperator jobOperator;

    @Autowired
    @Qualifier("rosterLoadJob")
    Job rosterLoadJob;

    @GetMapping("/test")
    @JsonView(DetailedView.class)
    public String test(){
        try {
            this.jobOperator.startNextInstance(rosterLoadJob.getName());
        } catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
