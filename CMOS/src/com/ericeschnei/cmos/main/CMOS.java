package com.ericeschnei.cmos.main;

import com.ericeschnei.cmos.renderer.SpriteBatch;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class CMOS {
    private final int WIDTH = 640;
    private final int HEIGHT = 480;

    private long window;
    private DoubleBuffer mouseX;
    private DoubleBuffer mouseY;

    public static void main(String[] args) {
        new CMOS().run();
    }

    private void run() {
        init();
        loop();
    }

    private void init() {

        GLFWErrorCallback.createPrint(System.err).set();
        mouseX = BufferUtils.createDoubleBuffer(1);
        mouseY = BufferUtils.createDoubleBuffer(1);


        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        createWindow();
        setCallbacks();

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        glfwSwapInterval(1); /* vsync */

        setupGL();

    }

    /* set up the window */
    private void createWindow() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(WIDTH, HEIGHT, "CMOS", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create window");
        }
        Input.getInstance().setScreenSize(WIDTH, HEIGHT);
    }

    /* setup callbacks */
    private void setCallbacks() {
        glfwSetMouseButtonCallback(window, (win, btn, act, mods) -> {
            boolean isPress = act == 1;
            if (btn == GLFW_MOUSE_BUTTON_LEFT) {
                Input.getInstance().setlMouse(isPress);

                if (isPress) {
                    lmPress((int) mouseX.get(0), (int) mouseY.get(0));
                } else {
                    lmRelease((int) mouseX.get(0), (int) mouseY.get(0));
                }
            } else if (btn == GLFW_MOUSE_BUTTON_RIGHT) {
                Input.getInstance().setrMouse(isPress);

                if (isPress) {
                    rmPress((int) mouseX.get(0), (int) mouseY.get(0));
                } else {
                    rmRelease((int) mouseX.get(0), (int) mouseY.get(0));
                }
            }


        });

        glfwSetKeyCallback(window, (win, key, sc, act, mods) -> {
            if (act == GLFW_PRESS) {
                Input.getInstance().setKey(key, true);
                keyPress(key);
            }
            if (act == GLFW_RELEASE) {
                Input.getInstance().setKey(key, false);
            }

        });

        glfwSetWindowSizeCallback(window, (window, x, y) -> {
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, x, y, 0, 1, -1);
            glMatrixMode(GL_MODELVIEW);
        });

        glfwSetFramebufferSizeCallback(window, (window, x, y) -> {
            Input.getInstance().setScreenSize(x, y);
            glViewport(0, 0, x, y);
            resize(x, y);
        });

        glfwSetScrollCallback(window, (win, x, y) -> {
            scroll(y);
        });
    }

    /* random openGL stuff */
    private void setupGL() {
        GL.createCapabilities();


        glClearColor(0f, 0f, 0f, 0f);

        glMatrixMode(GL_PROJECTION);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glViewport(0, 0, WIDTH, HEIGHT);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);
            glfwPollEvents();
            update();
            render();
            glfwSwapBuffers(window);
            glfwGetCursorPos(window, mouseX, mouseY);
            Input.getInstance().setMousePos((int) mouseX.get(0), (int) mouseY.get(0));

        }
    }

    private void update() {

    }

    private void render() {
        inputAndTextTest();
        SpriteBatch.getInstance().draw();
    }

    /* tests */
    private void inputAndTextTest() {
        String inputReadout1 = "LM: " + (Input.getInstance().islmPressed() ? "1" : "0") +
                " RM: " + (Input.getInstance().isrmPressed() ? "1" : "0") +
                " A: " + (Input.getInstance().isKeyPressed(GLFW_KEY_A) ? "1" : "0");
        String inputReadout2 = "MOUSE: " + String.valueOf(Input.getInstance().getMouseX()) +
                ", " + String.valueOf(Input.getInstance().getMouseY()) +
                " SCREEN: " + String.valueOf(Input.getInstance().getScreenX()) + ", " + String.valueOf(Input.getInstance().getScreenY());

        SpriteBatch.getInstance().addText(inputReadout1, 10, 10, 2, SpriteBatch.SPRITEBATCH_0DEG_NOFLIP, 1f, 1f, 1f, 1f);
        SpriteBatch.getInstance().addText(inputReadout2, 10, 22, 2, SpriteBatch.SPRITEBATCH_0DEG_NOFLIP, 1f, 1f, 1f, 1f);

        SpriteBatch.getInstance().addText("test", 200, 200, 4, SpriteBatch.SPRITEBATCH_0DEG_FLIP, 1f,0f,0f,1f);
        SpriteBatch.getInstance().addText("test", 200, 224, 4, SpriteBatch.SPRITEBATCH_0DEG_NOFLIP, 1f,1f,0f,1f);
        SpriteBatch.getInstance().addText("test", 200, 248, 4, SpriteBatch.SPRITEBATCH_180_FLIP, 0f, 1f, 0f, 1f);
        SpriteBatch.getInstance().addText("test", 200, 272, 4, SpriteBatch.SPRITEBATCH_180_NOFLIP, 0f, 1f, 1f, 1f);

        SpriteBatch.getInstance().addText("test", 296, 200, 4, SpriteBatch.SPRITEBATCH_90CW_NOFLIP, 0f, 0f, 1f, 1f);
        SpriteBatch.getInstance().addText("test", 320, 200, 4, SpriteBatch.SPRITEBATCH_90CW_FLIP, 1f, 0f, 1f, 1f);
        SpriteBatch.getInstance().addText("test", 344, 200, 4, SpriteBatch.SPRITEBATCH_90CCW_NOFLIP, 1f,1f,1f,.5f);
        SpriteBatch.getInstance().addText("test", 368, 200, 4, SpriteBatch.SPRITEBATCH_90CCW_FLIP, .25f, .5f,.75f ,.5f);
    }


    /* input callbacks */
    private void lmPress(int x, int y) {
        System.out.println("lmPress | " + x + ", " + y);
    }

    private void lmRelease(int x, int y) {
        System.out.println("lmRelease | " + x + ", " + y);
    }

    private void rmPress(int x, int y) {
        System.out.println("rmPress | " + x + ", " + y);
    }

    private void rmRelease(int x, int y) {
        System.out.println("rmRelease | " +  x + ", " + y);
    }

    private void keyPress(int key) {
        System.out.println("keyPress | " + key);
    }

    private void resize(int x, int y) {
        System.out.println("resize | " + x + ", " + y);
    }

    private void scroll(double y) {
        System.out.println("scroll | " + y);
    }
}