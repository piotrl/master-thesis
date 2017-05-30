package net.piotrl.music.web.home;

import net.piotrl.music.core.config.ApiUser;
import net.piotrl.music.modules.aggregation.repository.AggregationMetadataCrudRepository;
import net.piotrl.music.modules.aggregation.repository.AggregationMetadataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {

    private final AggregationMetadataCrudRepository metadataCrudRepository;

    @Autowired
    public SettingsController(AggregationMetadataCrudRepository metadataCrudRepository) {
        this.metadataCrudRepository = metadataCrudRepository;
    }

    @GetMapping("/settings")
    public String greetingForm(Model model, ApiUser apiUser) {
        AggregationMetadataEntity settings = getSettings(apiUser);

        model.addAttribute("settings", settings);
        return "home/settings";
    }

    @PostMapping("/settings")
    public String greetingSubmit(@ModelAttribute AggregationMetadataEntity newSettings,
                                 ApiUser apiUser) {
        AggregationMetadataEntity settings = getSettings(apiUser);
        settings.setLastfmApiKey(newSettings.getLastfmApiKey());
        settings.setLastfmUsername(newSettings.getLastfmUsername());
        settings.setRescuetimeApiKey(newSettings.getRescuetimeApiKey());
        metadataCrudRepository.save(settings);

        return "redirect:/settings";
    }

    private AggregationMetadataEntity getSettings(ApiUser apiUser) {
        AggregationMetadataEntity settings = metadataCrudRepository.findOne(apiUser.getId());
        if (settings == null) {
            settings = new AggregationMetadataEntity();
        }
        return settings;
    }

}
