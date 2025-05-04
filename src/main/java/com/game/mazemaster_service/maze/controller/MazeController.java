package com.game.mazemaster_service.maze.controller;

import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.ApiResponse;
import com.game.mazemaster_service.maze.dto.MazeResponse;
import com.game.mazemaster_service.maze.entity.LevelCategory;
import com.game.mazemaster_service.maze.service.MazeService;
import com.game.mazemaster_service.message.SystemMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/maze")
public class MazeController extends BaseController {
    private final MazeService mazeService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<MazeResponse>>> getMaze(@RequestParam(defaultValue = "EASY") LevelCategory levelCategory){
        return successResponse(mazeService.getAllMazeByCategory(levelCategory), SystemMessage.MAZE_FETCHED_SUCCESSFULLY);
    }

    @GetMapping("/level")
    public ResponseEntity<ApiResponse<List<MazeResponse>>> getMazeByLevel(
            @RequestParam(defaultValue = "EASY") LevelCategory levelCategory,
            @RequestParam(defaultValue = "1") int levelNumber
    ) {
        return successResponse(mazeService.getAllMazeByCategoryAndLevel(levelCategory, levelNumber), SystemMessage.MAZE_FETCHED_SUCCESSFULLY);
    }
}
