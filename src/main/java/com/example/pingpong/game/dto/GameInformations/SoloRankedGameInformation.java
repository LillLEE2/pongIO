//package com.example.pingpong.game.dto.GameInformations;
//
//import com.example.pingpong.game.dto.GameElements.GameElement;
//import com.example.pingpong.game.dto.GameObjects.*;
//import com.example.pingpong.game.dto.GameObjects.sendDataDTO.GameRoomIdMessage;
//import com.example.pingpong.game.dto.GameObjects.sendDataDTO.PaddleMoveData;
//import com.example.pingpong.game.dto.MatchingResult;
//import com.example.pingpong.game.service.GameResultsService;
//import com.example.pingpong.user.dto.UserQueue;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//
//import java.util.Iterator;
//import java.util.concurrent.ConcurrentLinkedQueue;
//import java.util.concurrent.ScheduledExecutorService;
//
//public class SoloRankedGameInformation extends GameInformation {
//    private String userSocketId;
//    private int leftPaddleStatus;
//    private GameElement gameElement;
//
//    public SoloRankedGameInformation(MatchingResult matchingResult, SimpMessagingTemplate messagingTemplate, GameResultsService gameResultsService) {
//        super(matchingResult, messagingTemplate, gameResultsService);
//        settingUserSocketIds(matchingResult.getUserQueue());
//        this.leftPaddleStatus = 0;
//        this.gameElement = new GameElement();
////        this.gameElement = super.getGameElement();
//    }
//
//    private void settingUserSocketIds(ConcurrentLinkedQueue<UserQueue> userQueue) {
//        Iterator<UserQueue> iterator = userQueue.iterator();
//        this.userSocketId = iterator.next().getSocketId();
//    }
//
//    public void positionUpdate(String roomName, String resultId) {
//        ballUpdate();
//        ballCollision();
//        boolean gameFinished = gameScoreCheck();
//        messagingTemplate.convertAndSend("/topic/position_update/" + roomName, this.gameElement);
//        if (gameFinished)
//            finishGame(roomName, resultId);
//    }
//
//    private void ballUpdate() {
//        this.gameElement.getPaddleList().get(0).update();
//
//        for (Ball ball : this.gameElement.getBallList()) {
//            ball.update(this.gameElement.getScore().getRankScore());
//        }
//    }
//
//    private void ballCollision() {
////        Ball ball = this.gameElement.getBallList().get(0);
//        Paddle leftPaddle = this.gameElement.getPaddleList().get(0);
//
//        for (Ball ball : this.gameElement.getBallList()) {
//            for (Item item : this.gameElement.getItemList()) {
//                if (isBallCollidingWithItem(ball, item)) {
//                    ball.applyItemEffect(item.getEffect());
//                    this.gameElement.getItemList().remove(item);
//                    break; // 볼이 한 번에 하나의 아이템만 획득하도록
//                }
//            }
//            ball.collision(gameElement, leftPaddle);
//        }
//    }
//
//    private boolean gameScoreCheck() {
////        Ball ball = gameElement.getBallList().get(0);
//        Integer score = gameElement.getScore().getRankScore();
//        Integer tempScore = score;
//        for (Ball ball : this.gameElement.getBallList()) {
//            tempScore -= 5;
//            if (ball.getPosX() < 0) {
//                return true;
//            }
//        }
//        if (tempScore > 0) {
//            gameElement.addBall();
//            gameElement.addRandomPositionItem();
//        }
//        return false;
//    }
//
//    private void updatePaddlePosition(int status, Paddle paddle) {
//        double speed = 3.0;
//        if (status == 1 && paddle.getPosY() > 0) {
//            paddle.setPosY(paddle.getPosY() - speed);
//        } else if (status == 2 && paddle.getPosY() < 100 - paddle.getHeight()) {
//            paddle.setPosY(paddle.getPosY() + speed);
//        }
//    }
//
//    public void paddleMove(SimpMessageHeaderAccessor accessor, PaddleMoveData data) {
//        System.out.println("/paddle_move/" + data.getGameRoomId() + " " + accessor.getSessionId());
//        this.leftPaddleStatus = data.getPaddleStatus();
//    }
//
//    private boolean isBallCollidingWithItem(Ball ball, Item item) {
//        double distance = Math.sqrt(Math.pow(ball.getPosX() - item.getPosX(), 2) + Math.pow(ball.getPosY() - item.getPosY(), 2));
//        return distance <= ball.getRadius() + item.getRadius();
//    }
//
//    private void finishGame(String roomName, String resultId) {
//        if (this.getTimer() != null) {
//            this.getTimer().cancel(false);
//        }
//        gameResultsService.finishGame(roomName, resultId);
//        messagingTemplate.convertAndSend("/topic/finish_game/" + roomName, 0);
//        System.out.println("game finished");
//    }
//
//    public void reStart(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data, ScheduledExecutorService executorService) {
//
//    }
//
//    public void exitUser(SimpMessageHeaderAccessor accessor, GameRoomIdMessage data) {
//    }
//}
//
