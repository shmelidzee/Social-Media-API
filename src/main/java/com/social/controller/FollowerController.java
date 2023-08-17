package com.social.controller;

import com.social.service.FollowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/followers")
@Tag(name = "Followers", description = "Followers API")
public class FollowerController {

    private final FollowerService followerService;

    @GetMapping({"/friends", "/friends/{userId}"})
    @Operation(summary = "Get my friends or friends other user",
            description = "Get my friends or friends other user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get my friends or friends other user"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Followers")
        public ResponseEntity<?> getFriends(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                        @PathVariable(value = "userId", required = false) Long userId,
                                        Principal principal) {
        return ResponseEntity.ok(null);
    }

    @GetMapping({"/subscribes", "/subscribes/{userId}"})
    @Operation(summary = "Get my subscribes or subscribes other user",
            description = "Get my subscribes or subscribes other user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get my subscribes or subscribes other user"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Followers")
    public ResponseEntity<?> getSubscribes(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                           @PathVariable(value = "userId", required = false) Long userId,
                                           Principal principal) {
        return ResponseEntity.ok(null);
    }

    @GetMapping({"/subscribers", "/subscribers/{userId}"})
    @Operation(summary = "Get my subscribers or subscribers other user",
            description = "Get my subscribes or subscribes other user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Get my subscribers or subscribers other user"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Followers")
    public ResponseEntity<?> getSubscribers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                            @PathVariable(value = "userId", required = false) Long userId,
                                            Principal principal) {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/follow/{userId}")
    @Operation(summary = "Follow to user and send invite to friends",
            description = "Follow to user and send invite to friends",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Follow to user and send invite to friends"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Followers")
    public ResponseEntity<Void> followOnUser(@PathVariable(name = "userId") Long userId,
                                             Principal principal) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/follow/accept/{userId}")
    @Operation(summary = "Accept invite in friends",
            description = "Accept invite in friends",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Accept invite in friends"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Followers")
    public ResponseEntity<Void> acceptFollow(@PathVariable(name = "userId") Long userId,
                                             Principal principal) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/follow/reject/{userId}")
    @Operation(summary = "Reject invite in friends",
            description = "Reject invite in friends",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reject invite in friends"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Followers")
    public ResponseEntity<Void> rejectFollow(@PathVariable(name = "userId") Long userId,
                                             Principal principal) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/unfollow/{userId}")
    @Operation(summary = "Unfollow user and delete his from friends",
            description = "Unfollow user and delete his from friends",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Unfollow user and delete his from friends"),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            },
            tags = "Followers")
    public ResponseEntity<Void> unfollowUser(@PathVariable(name = "userId") Long userId,
                                             Principal principal) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}