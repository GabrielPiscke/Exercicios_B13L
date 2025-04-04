package com.example.Curso.Controller;

import com.example.Curso.banco.CursoDb;
import com.example.Curso.model.Aluno;
import com.example.Curso.model.Curso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class CursoController {

    CursoDb repository = new CursoDb();

    public ResponseEntity<List<Curso>>getAll(){
        List<Curso> cursos = repository.findAll();
        if(cursos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.status(HttpStatus.FOUND).body(cursos);
        }
    }

    public ResponseEntity<List<Curso>> getByProfessor(String nomeProfessor){
        List<Curso> cursos = repository.findByProfessor(nomeProfessor);
        if(!cursos.isEmpty()){
            return ResponseEntity.status(HttpStatus.FOUND).body(cursos);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    public ResponseEntity<List<Curso>> getBySala(int sala){
        List<Curso> curso = repository.findBySala(sala);
       if(curso.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }else{
            return ResponseEntity.status(HttpStatus.FOUND).body(curso);
       }
    }

    public ResponseEntity<Curso> getById(int id){
        Curso curso = repository.getById(id);
        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body(curso);
        }
    }

    public ResponseEntity<Curso> insertBanco(Curso curso){
        Curso cursoSave = repository.insert(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoSave);
    }

    public ResponseEntity<ArrayList<Aluno>> insertAluno(int idCurso, Aluno aluno){
        Curso curso = repository.insertAluno(idCurso, aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(curso.getAlunos());
    }

    // esta funcao fara o mesmo insert do aluno que a funcao acima, porém com melhores práticas de programação
    public ResponseEntity<String> insertAlunoMelhorado(int idCurso, Aluno aluno){
        Curso curso = repository.getById(idCurso);
        if(curso == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado, para que aluno possa ser inserido!");
        }
        boolean result = repository.insertAlunoMelhorado(curso, aluno);
        if(result){
            return ResponseEntity.status(HttpStatus.CREATED).body("Aluno inserido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body( "Não foi possível inserir alunos!");
    }

    public ResponseEntity<String> update(int id, Curso curso){
        Curso curso1 = repository.getById(id);
        if(curso1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado!");
        } else {
            repository.update(id, curso);
            return ResponseEntity.status(HttpStatus.OK).body("Curso "+curso1.getNome()+" atualizado com sucesso!");
        }
    }

    public ResponseEntity<String> updateAluno(int idCurso, int idAluno, Aluno aluno){
        Curso curso =  repository.getById(idCurso);
        Curso aluno1 = repository.getById(idAluno);
        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado!");
        } if(aluno1 == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado!");
        }else {
            repository.updateAluno(idCurso, idAluno, aluno);
            return ResponseEntity.status(HttpStatus.OK).body("Aluno "+aluno.getNome()+" atualizado com sucesso!");
        }
    }

    public ResponseEntity<String> delete(int id){
        Curso curso = repository.getById(id);
        if(curso == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso "+curso.getNome()+" não encontrado!");
        }else{
            repository.delete(id);
            return ResponseEntity.ok("Curso "+ curso.getNome() +" deletado com sucesso!");
        }
    }
}
