package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.math.*;
import com.chernobyl.gameengine.render.Shader;

import java.io.*;
import java.util.HashMap;
import java.util.Vector;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.Log.HB_CORE_ERROR;
import static com.chernobyl.gameengine.Log.HB_CORE_INFO;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class OpenGLShader extends Shader {
    private int m_RendererID;

    public OpenGLShader(String filepath) {
        String source = ReadFile(filepath);
        var shaderSources = PreProcess(source);
        Compile(shaderSources);
    }

    public OpenGLShader(String vertexSrc, String fragmentSrc) {
        HashMap<Integer, String> sources = new HashMap<>();
        sources.put(GL_VERTEX_SHADER, vertexSrc);
        sources.put(GL_FRAGMENT_SHADER, fragmentSrc);
        Compile(sources);
    }

    static int ShaderTypeFromString(String type) {
        if (type.equals("vertex"))
            return GL_VERTEX_SHADER;
        if (type.equals("fragment") || type.equals("pixel"))
            return GL_FRAGMENT_SHADER;

        HB_CORE_ASSERT(false, "Unknown shader type!");
        return 0;
    }

    String ReadFile(String filepath) {
        File file = new File(filepath);
        StringBuilder result = new StringBuilder((int) file.length());

        BufferedReader in;
        try {
            var fr = new FileReader(file);

            in = new BufferedReader(fr);
            while (in.ready()) {
                result.append(in.readLine()).append("\r\n");
            }
        } catch (IOException e) {
            HB_CORE_ERROR("Could not read file '{0}', exception: {1}", filepath, e);
        }
        HB_CORE_ASSERT(!result.isEmpty(), "Shader Empty!!");
        return result.toString();
    }

    HashMap<Integer, String> PreProcess(String source) {
        HashMap<Integer, String> shaderSources = new HashMap<>();

        String typeToken = "#type";
        int typeTokenLength = typeToken.length();
        int pos = source.indexOf(typeToken);
        while (pos != -1) {
            int eol = source.indexOf("\r\n", pos);
            HB_CORE_ASSERT(eol < source.length(), "Syntax error");

            int begin = pos + typeTokenLength + 1;
            String type = source.substring(begin, eol).trim();
            HB_CORE_ASSERT(ShaderTypeFromString(type) != 0, "Invalid shader type specified");

            int nextLinePos = source.indexOf("\r\n", eol);
            pos = source.indexOf(typeToken, nextLinePos);

            var shader =  source.substring(
                    nextLinePos,
                    pos == -1 ? source.lastIndexOf("\r\n") : pos
            );

            shaderSources.put(ShaderTypeFromString(type), shader);
        }

        return shaderSources;
    }

    void Compile(HashMap<Integer, String> shaderSources) {
        int program = glCreateProgram();
        Vector<Integer> glShaderIDs = new Vector<>(shaderSources.size());
        for (var kv : shaderSources.entrySet()) {
            int type = kv.getKey();
            String source = kv.getValue();

            int shader = glCreateShader(type);

            glShaderSource(shader, source);

            glCompileShader(shader);

            int isCompiled = 0;
            int[] params = {isCompiled};
            glGetShaderiv(shader, GL_COMPILE_STATUS, params);
            isCompiled = params[0];
            if (isCompiled == GL_FALSE) {
                int maxLength;
                glGetShaderiv(shader, GL_INFO_LOG_LENGTH, params);
                maxLength = params[0];

                String infoLog = glGetShaderInfoLog(program, maxLength);

                glDeleteShader(shader);

                HB_CORE_ERROR("{0}", infoLog);
                HB_CORE_ASSERT(false, "Shader compilation failure!");
                break;
            }

            glAttachShader(program, shader);
            glShaderIDs.addElement(shader);
        }

        m_RendererID = program;

        // Link our program
        glLinkProgram(program);

        // Note the different functions here: glGetProgram* instead of glGetShader*.
        int isLinked = 0;
        int[] params = {isLinked};
        glGetProgramiv(program, GL_LINK_STATUS, params);
        isLinked = params[0];
        if (isLinked == GL_FALSE) {
            int maxLength;
            glGetProgramiv(program, GL_INFO_LOG_LENGTH, params);
            maxLength = params[0];

            // The maxLength includes the NULL character
            String infoLog = glGetProgramInfoLog(program, maxLength);

            // We don't need the program anymore.
            glDeleteProgram(program);

            for (var id : glShaderIDs)
                glDeleteShader(id);

            HB_CORE_ERROR("{0}", infoLog);
            HB_CORE_ASSERT(false, "Shader link failure!");
            return;
        }

        for (var id : glShaderIDs)
            glDetachShader(program, id);
    }


    public void destroy() {
        glDeleteProgram(m_RendererID);
    }

    public void Bind() {
        glUseProgram(m_RendererID);
    }

    public void Unbind() {
        glUseProgram(0);
    }

    public void UploadUniformInt(String name, int value) {
        int location = glGetUniformLocation(m_RendererID, name);
        glUniform1i(location, value);
    }

    public void UploadUniformFloat(String name, float value) {
        int location = glGetUniformLocation(m_RendererID, name);
        glUniform1f(location, value);
    }

    public void UploadUniformFloat2(String name, Vec2 value) {
        int location = glGetUniformLocation(m_RendererID, name);
        glUniform2f(location, value.x, value.y);
    }

    public void UploadUniformFloat3(String name, Vec3 value) {
        int location = glGetUniformLocation(m_RendererID, name);
        glUniform3f(location, value.x, value.y, value.z);
    }

    public void UploadUniformFloat4(String name, Vec4 value) {
        int location = glGetUniformLocation(m_RendererID, name);
        glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    public void UploadUniformMat3(String name, Mat3 matrix) {
        int location = glGetUniformLocation(m_RendererID, name);
        glUniformMatrix3fv(location, false, matrix.toArray());
    }

    public void UploadUniformMat4(String name, Mat4 matrix) {
        int location = glGetUniformLocation(m_RendererID, name);
        glUniformMatrix4fv(location, false, matrix.toArray());
    }
}
