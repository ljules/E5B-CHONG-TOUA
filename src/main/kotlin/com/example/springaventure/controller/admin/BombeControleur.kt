package com.example.springaventure.controller.admin

import com.example.springaventure.model.dao.BombeDao
import com.example.springaventure.model.entity.Armure
import com.example.springaventure.model.entity.Bombe
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

/**
 * Contrôleur responsable de la gestion des bombes dans la partie administrative de l'application.
 */
@Controller
class BombeControleur(
    /** DAO pour l'accès aux données des bombes. */
    val bombeDao: BombeDao
) {

    /**
     * Affiche la liste des bombes dans la page d'index.
     *
     * @param model Modèle utilisé pour transmettre des données à la vue.
     * @return Le nom de la vue à afficher.
     */
    @GetMapping("/admin/bombe")
    fun index(model: Model): String {
        // Récupère toutes les bombes depuis la base de données
        val bombes = this.bombeDao.findAll()
        // Ajoute la liste des bombes au modèle pour transmission à la vue
        model.addAttribute("bombes", bombes)
        // Retourne le nom de la vue à afficher
        return "admin/bombe/index"
    }

    @GetMapping("/admin/bombe/create")
    fun create(model: Model): String {
        val nouvelleBombe = Bombe(null, "", "", "",0,0)
        model.addAttribute("nouvelleBombe", nouvelleBombe)
        return "admin/bombe/create"
    }

    @PostMapping("/admin/bombe")
    fun store(@ModelAttribute nouvelleBombe: Bombe, redirectAttributes: RedirectAttributes): String {
        val savedBombe = this.bombeDao.save(nouvelleBombe)
        redirectAttributes.addFlashAttribute("msgSuccess", "Enregistrement de ${savedBombe.nom} réussi")
        return "redirect:/admin/bombe"
    }

    @GetMapping("/admin/bombe/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val bombe = this.bombeDao.findById(id).orElseThrow()
        model.addAttribute("bombe", bombe)
        return "admin/bombe/show"
    }

    @GetMapping("/admin/bombe/{id}/edit")
    fun edit(@PathVariable id: Long, model: Model): String {
        val bombe = this.bombeDao.findById(id).orElseThrow()
        model.addAttribute("bombe", bombe)
        return "admin/bombe/edit"
    }

    @PostMapping("/admin/bombe/update")
    fun update(@ModelAttribute bombe: Bombe, redirectAttributes: RedirectAttributes): String {
        val bombeModifier = this.bombeDao.findById(bombe.id ?: 0).orElseThrow()

        // Valorise les attributs de armureModifier, par exemple : armureModifier.nom = armure.nom
        bombeModifier.nom = bombe.nom
        bombeModifier.cheminImage = bombe.cheminImage
        bombeModifier.description = bombe.description
        bombeModifier.nbrDes = bombe.nbrDes
        bombeModifier.maxDes = bombe.maxDes

        val savedBombe = this.bombeDao.save(bombeModifier)
        redirectAttributes.addFlashAttribute("msgSuccess", "Modification de ${savedBombe.nom} réussie")
        return "redirect:/admin/bombe"
    }

    @PostMapping("/admin/bombe/delete")
    fun delete(@RequestParam id: Long, redirectAttributes: RedirectAttributes): String {
        val bombe = this.bombeDao.findById(id).orElseThrow()
        this.bombeDao.delete(bombe)
        redirectAttributes.addFlashAttribute("msgSuccess", "Suppression de ${bombe.nom} réussie")
        return "redirect:/admin/bombe"
    }
}
