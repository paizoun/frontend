import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

import { LANGUAGES } from 'app/core/language/language.constants';

@Component({
  selector: 'jhi-footer',
  templateUrl: './footer.component.html'
})
export class FooterComponent {
  languages = LANGUAGES;

  constructor(private languageService: JhiLanguageService) {}

  changeLanguage(languageKey: string): void {
    this.languageService.changeLanguage(languageKey);
  }
}
