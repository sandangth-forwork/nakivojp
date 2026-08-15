package sanlab.itv.nakivojpslaveowner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sanlab.itv.nakivojpslaveowner.service.CrudJobService;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final CrudJobService crudJobService;


}
