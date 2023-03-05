package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Controller
public class CodeController {

    @Autowired
    private CodeRepository codeRepository;

    private static final ConcurrentMap<String, Boolean> timeDecrementThreadsMap = new ConcurrentHashMap<>();

    @GetMapping(value = "/code/{uuid}", produces = MediaType.TEXT_HTML_VALUE)
    public String getCodeHTML(Model singleCode , @PathVariable String uuid, HttpServletResponse response) {
        Code code = codeRepository.findById(uuid).orElse(null);

        if (code == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return "error";
        }

        if (code.isRestricted()) {
            if (code.getViews() > 1) {
                code.setViews(code.getViews()-1);
                codeRepository.save(code);
            } else if (code.getViews() == 1) {
                code.setViews(code.getViews()-1);
                codeRepository.save(code);
                codeRepository.delete(code);
            }
        }

        singleCode.addAttribute("code", code);
        return "singleCode";
    }

    @GetMapping(value = "/api/code/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CodeResponse> getCodeJSON(@PathVariable String uuid) {
        Optional<Code> codeOptional = codeRepository.findById(uuid);
        if (codeOptional.isPresent()) {
            Code code = codeOptional.get();

            if (code.isRestricted()) {
                if (code.getViews() > 1) {
                    code.setViews(code.getViews()-1);
                    codeRepository.save(code);
                } else if (code.getViews() == 1) {
                    code.setViews(code.getViews()-1);
                    codeRepository.save(code);
                    codeRepository.delete(code);
                }
            }

            CodeResponse codeResponse = new CodeResponse(code.getCode(), code.getDate(), code.getTime(), code.getViews());
            return ResponseEntity.ok(codeResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/api/code/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateCodeJSON(@RequestBody CodeResponse newCode) {
        Code code = new Code(newCode.getCode(), newCode.getTime(), newCode.getViews());
        Code savedCode = codeRepository.save(code);
        String uuid = String.valueOf(savedCode.getUuid());
        Map<String, String> response = new HashMap<>();
        response.put("id", uuid);

        if (code.isRestricted() && code.getTime() > 0) {
            startCodeTimeDecrementThread(code);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getCodeForm() {
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <script>\n" +
                "function send() {\n" +
                "    let object = {\n" +
                "        \"code\": document.getElementById(\"code_snippet\").value\n" +
                "    };\n" +
                "    \n" +
                "    let json = JSON.stringify(object);\n" +
                "    \n" +
                "    let xhr = new XMLHttpRequest();\n" +
                "    xhr.open(\"POST\", '/api/code/new', false)\n" +
                "    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');\n" +
                "    xhr.send(json);\n" +
                "    \n" +
                "    if (xhr.status == 200) {\n" +
                "      alert(\"Success!\");\n" +
                "    }\n" +
                "}" +
                "  </script>\n" +
                "  <title>Create</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <form>\n" +
                "    <textarea id=\"code_snippet\" cols=\"100\" rows=\"10\" placeholder=\"// write your code here\"></textarea>\n" +
                "  </form>\n" +
                "  <button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>\n" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping(value = "/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Code>> getLatestCodeJSON() {
        List<Code> latestCodes = codeRepository.findAllByIsRestrictedFalseOrderByDateDesc()
                .stream()
                .limit(10)
                .collect(Collectors.toList());
        return ResponseEntity.ok(latestCodes);
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String getLatestCodeHTML(Model latest) {
        List<Code> latestCodes = codeRepository.findAllByIsRestrictedFalseOrderByDateDesc()
                .stream()
                .limit(10)
                .collect(Collectors.toList());
        latest.addAttribute("codes", latestCodes);
        return "latest";
    }

    private void startCodeTimeDecrementThread(Code code) {
        if (timeDecrementThreadsMap.putIfAbsent(code.getUuid(), true) == null) {
            new Thread(() -> {
                while (code.getTime() > 0) {
                    try {
                        Thread.sleep(300);
                        code.setTime(code.getTime() - 1);
                        codeRepository.save(code);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                codeRepository.delete(code);
                timeDecrementThreadsMap.remove(code.getUuid());
            }).start();
        }
    }
}
