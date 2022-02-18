package com.chernobyl.platform.opengl;

import com.chernobyl.gameengine.math.*;
import com.chernobyl.gameengine.render.Shader;

import static com.chernobyl.gameengine.Asserts.HB_CORE_ASSERT;
import static com.chernobyl.gameengine.Log.HB_CORE_ERROR;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class OpenGLShader extends Shader {
    private int m_RendererID;

    public OpenGLShader(String vertexSrc, String fragmentSrc) {
        // Create an empty vertex shader handle
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);

        // Send the vertex shader source code to GL
        // Note that String's .c_str is NULL character terminated.
        var source = new StringBuilder(vertexSrc);
        glShaderSource(vertexShader, source);

        // Compile the vertex shader
        glCompileShader(vertexShader);

        int isCompiled = GL_FALSE;
        int[] params = {isCompiled};

        glGetShaderiv(vertexShader, GL_COMPILE_STATUS, params);
        isCompiled = params[0];
        if (isCompiled == GL_FALSE) {
            int maxLength;
            glGetShaderiv(vertexShader, GL_INFO_LOG_LENGTH, params);
            maxLength = params[0];

            // The maxLength includes the NULL character
            String infoLog = glGetShaderInfoLog(vertexShader, maxLength);

            // We don't need the shader anymore.
            glDeleteShader(vertexShader);

            HB_CORE_ERROR("{0}", infoLog);
            HB_CORE_ASSERT(false, "Vertex shader compilation failure!");
            return;
        }

        // Create an empty fragment shader handle
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        // Send the fragment shader source code to GL
        // Note that String's .c_str is NULL character terminated.
        source = new StringBuilder(fragmentSrc);
        glShaderSource(fragmentShader, source);

        // Compile the fragment shader
        glCompileShader(fragmentShader);

        glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, params);
        isCompiled = params[0];
        if (isCompiled == GL_FALSE) {
            int maxLength;
            glGetShaderiv(fragmentShader, GL_INFO_LOG_LENGTH, params);
            maxLength = params[0];

            // The maxLength includes the NULL character
            String infoLog = glGetShaderInfoLog(vertexShader, maxLength);

            // We don't need the shader anymore.
            glDeleteShader(fragmentShader);
            // Either of them. Don't leak shaders.
            glDeleteShader(vertexShader);

            HB_CORE_ERROR("{0}", infoLog);
            HB_CORE_ASSERT(false, "Fragment shader compilation failure!");
            return;
        }

        // Vertex and fragment shaders are successfully compiled.
        // Now time to link them together into a program.
        // Get a program object.
        m_RendererID = glCreateProgram();
        int program = m_RendererID;

        // Attach our shaders to our program
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        // Link our program
        glLinkProgram(program);

        // Note the different functions here: glGetProgram* instead of glGetShader*.
        int isLinked;
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
            // Don't leak shaders either.
            glDeleteShader(vertexShader);
            glDeleteShader(fragmentShader);

            HB_CORE_ERROR("{0}", infoLog);
            HB_CORE_ASSERT(false, "Shader link failure!");
            return;
        }

        // Always detach shaders after a successful link.
        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);
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
