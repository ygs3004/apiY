package kr.co.apiy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Stack;
import java.util.stream.IntStream;

@SpringBootTest
@ActiveProfiles("test")
class ApiYApplicationTests {

    @Test
    void contextLoads() {
    }
    public int[] solution(int[] arr, boolean[] flag) {
        Stack<Integer> stack = new Stack<>();
        IntStream
                .range(0, flag.length)
                .forEach(idx -> {
                    if(flag[idx]){
                        for(int i = 0; i < arr[i] * 2; i++){
                            stack.push(arr[i]);
                        }
                    }else{
                        stack.pop();
                    }
                });

        return stack.stream().mapToInt(Integer::intValue).toArray();
    }
}
