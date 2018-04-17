/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.pos.twitter.web;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author wellington
 */
public class MensaoDaDos implements Serializable,Comparable<MensaoDaDos>{
    
    
    String nome;
    Integer quantidade;

    public MensaoDaDos() {
    }

    public MensaoDaDos(String nome, Integer quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "nome=" + nome + ", quantidade=" + quantidade;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.nome);
        hash = 17 * hash + Objects.hashCode(this.quantidade);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MensaoDaDos other = (MensaoDaDos) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.quantidade, other.quantidade)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(MensaoDaDos o) {
     if (this.quantidade > o.quantidade) {
            return -1;
        }
        if (this.quantidade < o.quantidade) {
            return 1;
        }
        return 0;
    }
    
    
    
}
